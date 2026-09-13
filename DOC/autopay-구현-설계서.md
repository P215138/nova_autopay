# autopay 구현 설계서

> 작성 근거: `c:\nova\autopay` 실제 구현 코드 (2026-09)
> 대상: SKT 자동납부(autopay) 납부수단관리 시스템
> 기술: Spring Boot 3.3.0 / Java 23 / MyBatis-Plus 3.5.7 / MySQL 8.0

---

## 1. 개요

autopay는 통신사 빌링·수납의 **납부수단관리** 도메인을 헥사고날(포트-어댑터) 아키텍처로 구현한 차세대 시스템이다.
핵심은 고객이 소유한 결제수단(은행/카드/간편결제)을 인증 후 등록하고, 청구계정·납부계정과 연결하는 것이다.

### 핵심 원칙
- **auth-first**: 금융기관 실시간인증을 먼저 수행하고, 성공한 경우에만 납부수단을 저장한다.
- **인증이력 보존**: 인증 요청/결과 이력을 별도 트랜잭션으로 남겨, 인증 실패로 본 트랜잭션이 롤백돼도 이력은 유지한다.
- **멀티테넌트**: 모든 엔티티가 `TENANT_ID`를 가진다.
- **포트-어댑터**: 외부 연동(금융인증, SMS)은 포트로 추상화하고, 스텁/실제 어댑터를 프로파일로 전환한다.

---

## 2. 모듈 구조 (Gradle 멀티모듈)

```
autopay/
├── contract/          모듈 간 공개 계약 (Contract API, DTO). spring-context만 의존
├── app/               핵심 비즈니스 로직 (core+orchestration+presentation+external 통합)
├── client-rest/       외부 금융기관 REST 연동 어댑터 (@Profile rest-live)
├── client-message/    MQ/EAI 연동 어댑터
└── boot/              실행 애플리케이션 (조립 + application.yml + schema.sql + static)
```

> BE 아키텍처 간소화(2026-09-10)로 기존 8모듈 → 5모듈로 통합. `core/orchestration/presentation/external`을 `app`으로 합침.

### 의존 방향
```
boot → (app, client-rest, client-message) → contract
```

### 실행 설정 (boot/application.yml)
- 포트: **8081**
- DB: `jdbc:mysql://localhost:3306/nova_db`, user `P215138`, password `${DB_PASSWORD}`
- MyBatis-Plus: `map-underscore-to-camel-case`, `id-type: input`(직접 채번)
- 정적 리소스 캐시 0 + DevTools livereload (개발 편의)
- 외부 금융 base-url: `autopay.external.finance.base-url` (기본 http://localhost:9900)

---

## 3. 도메인 패키지 구조 (app: `com.skt.autopay`)

| 패키지 | 역할 |
|--------|------|
| `customer` | 고객 |
| `billing` | 청구계정 |
| `paymeansregistration` | 납부수단 등록 (핵심) |
| `realtimeregistration` | 실시간 자동납부 등록/변경/해지, 납부계정, 분리납부 등 |
| `orchestration` | 원장 인증 오케스트레이션 |
| `autopaynotice` | 알림 |
| `common.web` | 전역 예외 처리 |

패키지 내부 컨벤션:
```
application/  command · query · dto · port
domain/       model (엔티티·enum·정책)
infra/        persistence (entity · mapper · repository)
rest/         openapi.internal (컨트롤러)
adapter/      out (외부연동 어댑터)
```

---

## 4. 상태코드 / Enum

### 4.1 납부수단상태코드 (PayMeansStatus / MEANS_STAT_CD)
| 코드 | enum | 의미 |
|------|------|------|
| 01 | REGISTERING | 등록중 (비대면 대리납부 소유주 동의 대기) |
| 02 | AUTH_WAITING | 인증대기 (금융기관 장애·비동기) |
| 10 | ACTIVE | 활성 |
| 20 | SUSPENDED | 정지 (신규 매핑 차단, 기존 유지) |
| 30 | TERMINATED | 해지 (논리 삭제) |
| 40 | CLOSED | 종료 (동의 거부/지연, 인증 최종 실패) |

### 4.2 납부수단유형 (PayMeansType / PAY_MEANS_TYPE_CD)
| 코드 | enum | 의미 | 대리납부 |
|------|------|------|:---:|
| 01 | BANK | 은행 | 가능 |
| 02 | CARD | 카드 | 가능 |
| 05 | SIMPLE_PAY | 간편결제 | **불가** |

### 4.3 대리납부맵 상태 (AgentPayMapStatus / AGENT_PAY_MAP_STAT_CD)
| 코드 | enum | 의미 |
|------|------|------|
| 10 | REQ | 요청 (소유주 동의 대기) |
| 20 | ACT | 활성 (동의 완료) |
| 30 | REJ | 거절 |
| 40 | STOP | 중단 (미이용 배치) |
| 50 | TRM | 종료 (관계 해지) |

### 4.4 실시간인증상태 (RealtmAuthStatus / AUTHENTC_STAT_CD)
| 코드 | enum | 의미 |
|------|------|------|
| 01 | REQUESTED | 인증중 |
| 10 | SUCCESS | 성공 |
| 40 | FAILED | 실패 |

### 4.5 대리인 등록방식 (AgentRegistrationType)
- FACE_TO_FACE(대면): 즉시 활성(10)
- NON_FACE(비대면): 등록중(01) + 소유주 동의 대기

---

## 5. 채번 규칙

| 대상 | 규칙 | 예 |
|------|------|-----|
| 고객번호 | `9` + 9자리 (10자리) | 9012345678 |
| 청구계정번호 | `2` + 9자리 (10자리) | 2012345678 |
| 납부계정번호 | `3` + 9자리 (Long) | 3012345678 |
| 실시간인증순번 | `currentTimeMillis * 1000 + 랜덤` | |

> 성별은 주민번호 뒷자리 첫 숫자로 자동 판별 (1/3/5/7/9 → M, 2/4/6/8/0 → F). 등록자/변경자 ID는 `P215138` 고정 자동 채움.

---

## 6. 주요 기능 / API

### 6.1 고객 — `/api/autopay/customers`
| Method | Path | 기능 |
|--------|------|------|
| POST | `` | 고객 등록 (번호 미지정 시 채번) |
| GET | `` | 목록 |
| GET | `/{no}` | 단건 |
| GET | `/by-rrn?rrn=` | 주민번호 조회 (하이픈 무관) |

### 6.2 청구계정 — `/api/autopay/billing-accounts`
| Method | Path | 기능 |
|--------|------|------|
| POST | `` | 등록 |
| GET | `?tenantId&customerNo` | 고객 기준 목록 (없으면 전체) |

### 6.3 납부수단 — `/api/autopay/pay-means`
| Method | Path | UC | 기능 |
|--------|------|-----|------|
| POST | `` | U1 | 본인 등록 |
| GET | `?tenantId&customerNo` | U3 | 지갑 목록 |
| GET | `/{no}` | | 단건 (payMeansId 단독) |
| POST | `/{no}/change` | U4 | 별칭 변경 |
| POST | `/{no}/disable` | U6 | 사용중지 |
| POST | `/{no}/terminate` | U7 | 해지 |
| POST | `/agent-requests` | U9 | 대리납부 등록 (대면/비대면) |
| POST | `/agent-requests/{seqno}/complete` | U10 | 비대면 승인/거절 |
| GET | `/agent-maps?tenantId&customerNo` | U11 | 대리납부 맵핑 조회 (피대리납부자 기준) |
| GET | `/agent-requests/pending?tenantId&customerNo` | | 승인대기 목록 (소유주 기준) |

### 6.4 납부계정 — `/api/autopay/payment-accounts`
| Method | Path | 기능 |
|--------|------|------|
| POST | `` | 등록 (청구계정 + 메인/예비 납부수단 연결) |
| GET | `?tenantId&invoiceAcctNo` | 청구계정 기준 목록 (1:N) |

---

## 7. 핵심 흐름: 납부수단 등록 (auth-first)

### 7.1 본인 등록 (U1, registerByOwner)
```
1. 중복 체크 — 동일 계좌/카드(대체ID)가 유효(01/02/10) 상태로 존재하면 등록 불가
2. auth-first 인증 (authenticateWithHistory)
   2-1. 인증이력 INSERT (01 인증중) — REQUIRES_NEW
   2-2. 금융기관 인증 실행 (FinancialAuthGatewayPort)
   2-3. 인증이력 UPDATE (성공 10 / 실패 40) — REQUIRES_NEW
   2-4. 실패 시 예외 → PayMeans 저장 안 함
3. PayMeans 생성 (상태 = 활성 10) 저장
4. 인증이력에 채번된 payMeansNo 링크 (linkPayMeans)
```

### 7.2 대리인 등록 (U9, registerByAgent)
```
1. 간편결제(05)면 거부
2. 중복 체크 1 — 동일 대리납부자↔피대리납부자 관계가 진행중(REQ)/활성(ACT)이면 거부
3. 중복 체크 3 — 동일 계좌/카드 1건만
4. auth-first 인증 (동일)
5. 수단 소유주(=대리납부자) 명의로 PayMeans 생성
   - 대면   : 활성(10) + AgentPayMap 활성(ACT)
   - 비대면 : 등록중(01) + AgentPayMap 요청(REQ) + 소유주 SMS 동의요청 발송
```

### 7.3 대리납부 승인 (U10, completeConsent)
```
승인: PayMeans 01→10, AgentPayMap 요청→활성
거절: PayMeans 01→40, AgentPayMap 요청→거절(+soft delete)
```

### 7.4 인증이력 트랜잭션 전략
- `createRequested`, `applyResult`는 `@Transactional(REQUIRES_NEW)` — 인증 실패로 본 트랜잭션이 롤백돼도 이력은 커밋되어 남는다.
- `applyResult`는 재조회 없이 `LambdaUpdateWrapper` 조건 UPDATE (REQUIRES_NEW 격리로 인한 "미조회" 방지).

---

## 8. 중복 등록 정책

| 기준 | 내용 | 적용 |
|------|------|------|
| 기준1 | 동일 대리납부자↔피대리납부자 관계 중복(REQ/ACT) 불가 | 대리 등록 |
| 기준3 | 동일 계좌/카드는 본인·대리 무관 1건만 (유효상태 01/02/10) | 본인·대리 등록 |

---

## 9. 외부 연동 (포트-어댑터)

### 9.1 금융기관 인증 — FinancialAuthGatewayPort
- `FinancialAuthStubAdapter` (app, `@Profile("!rest-live")`) — 항상 성공 반환(개발/데모)
- `FinancialAuthRestAdapter` (client-rest, `@Profile("rest-live")`) — 실제 REST 호출
  - 은행(01) → `/ext/finance/account/verify` (KS-NET 계좌실명)
  - 카드(02) → `/ext/finance/card/verify` (카드사 유효성)

### 9.2 대리납부 동의요청 — SmsAuthGatewayPort
- `SmsAuthStubAdapter` (app) — 로그만 남기고 성공 반환

### 9.3 인증이력 — RealtmAuthHistoryPort
- `RealtmAuthHistoryRepositoryAdapter` — PAY_MEANS_REALTM_AUTHENTC 기록/업데이트

> 프로파일 전환: 기본은 스텁, `--spring.profiles.active=rest-live` 지정 시 실제 REST 어댑터로 교체. 서비스 코드 변경 없음.

---

## 10. DB 스키마 (11개 테이블)

공통 감사 컬럼: `FIRST_REGIST_DTM`, `FIRST_REGISTR_ID`, `FINAL_CHG_DTM`, `FINAL_CHGR_ID`

| # | 테이블 | PK | 비고 |
|---|--------|-----|------|
| 1 | PAY_MEANS | PAY_MEANS_NO | 납부수단 |
| 2 | PAY_ACCT | PAY_ACCT_NO+TENANT_ID+PAY_CATG_CD+RESERVE_PAY_MEANS_NO | 납부계정 (청구계정 1:N) |
| 3 | PAY_ACCT_RCPTN | PAY_ACCT_NO+PAY_MEANS_SEQ+TENANT_ID+AUTO_PAY_REGIST_SEQ | 납부계정접수 |
| 4 | PAY_ACCT_AUTHENTC | PAY_ACCT_NO+PAY_MEANS_SEQ+TENANT_ID+AUTHENTC_SEQ | 납부계정인증 |
| 5 | PAY_ACCT_AUTHENTC_WTT | HSHLD+PAY_ACCT_NO+PAY_MEANS_SEQ+TENANT_ID | 납부계정인증(WTT) |
| 6 | PAY_MEANS_REALTM_AUTHENTC | REALTM_AUTHENTC_SEQ+TENANT_ID | 납부수단실시간인증 (PAY_MEANS_ID는 성공 후 채움) |
| 7 | EXTERNAL_INST_AUTO_PAY_APPREQ | EXTNL_INST_CD+AUTO_PAY_APPREQ_SEQ+TENANT_ID | 외부기관자동납부신청 |
| 8 | EXTERNAL_INST_AUTO_PAY_APPREQ_DTL | +DTL_SEQ | 상세 |
| 9 | PAY_AGENT_MAP | AGENT_PAY_MAP_SEQNO | 대리납부맵핑 (소유주/사용자, soft delete) |
| 10 | CUSTOMER | CUSTOMER_NO | 고객 (이름/주민번호/성별/이동전화) |
| 11 | BILLING_ACCOUNT | BILLING_ACCOUNT_NO | 청구계정 |

> ⚠ PAY_MEANS/PAY_ACCT는 테이블 정의서(엑셀) 기반, 나머지는 entity-definitions 한글명을 영문 물리명으로 변환. 이미지 판독/명명 변환으로 일부 컬럼명·타입에 오차 가능성 있음(원본 대조 필요).

---

## 11. 화면 (boot/static)

| 파일 | 역할 |
|------|------|
| index.html | 좌측 사이드 메뉴 + iframe 대시보드 |
| customer.html | 고객 등록/목록 |
| billing-account.html | 청구계정 등록/목록 (주민번호로 고객 조회) |
| pay-means.html | 납부수단 등록 (관계 콤보: 본인/부모/배우자/자녀, 대리 시 대면·비대면) |
| agent-consent.html | 대리납부 소유주 승인/거절 (U10) |
| payment-account.html | 납부계정 등록 (고객 조회 → 청구계정/납부수단 선택, 없으면 인라인 신규 등록) |

**메뉴 구성**
```
autopay
├─ 기준정보
│   ├─ 고객 등록
│   └─ 청구계정 등록
├─ 납부수단
│   ├─ 납부수단 등록
│   └─ 대리납부 동의
└─ 납부계정
    └─ 납부계정 등록
```

---

## 12. 전체 업무 흐름 (화면 기준)

```
1. 고객 등록          customer.html
2. 청구계정 등록       billing-account.html   (고객에 연결)
3. 납부수단 등록       pay-means.html          (고객 소유 수단, auth-first 인증)
   └ 비대면 대리 시 → agent-consent.html      (소유주 승인)
4. 납부계정 등록       payment-account.html    (청구계정 + 납부수단 메인/예비 연결)
```

이로써 **청구계정 1 : 납부계정 N, 납부계정 ↔ 납부수단(메인+예비)** 구조가 완성된다.

---

## 13. 미완/유의 사항 (TODO)

- 계좌/카드번호는 현재 데모용 해시(대체ID)로 처리. 실제로는 토큰화/암호화 저장 + 마스킹 표시 필요.
- FinancialAuthRestAdapter의 외부 API 경로/필드는 임시 정의. 실제 KS-NET/카드사 규격 확정 필요.
- realtimeregistration의 다수 서비스(자동납부 신청/변경/해지, 분리납부, 원장인증 등)는 골격 위주이며 상세 구현은 진행 중.
- schema.sql 코드성 컬럼 길이는 실데이터 기준으로 점진 조정 중 (원본 테이블 정의서 대조 권장).
