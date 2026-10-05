# autopay 프로젝트 개발 컨텍스트

> 프로젝트 위치: `c:\nova\autopay`
> 기술 스택: Spring Boot 3.3.x / Java 23 / MyBatis-Plus / MySQL 8.0
> 실행 포트: 8081 / DB: nova_db / User: P215138 / Password: park2019**
> GitHub: https://github.com/P215138/nova_autopay (branch: feature/autopay)
> git user: HCPARK / newsplus@nate.com (저장소 한정 설정)

---

## 멀티모듈 구성

```
autopay/
├── app/          핵심 도메인·유스케이스·영속·컨트롤러
├── boot/         Spring Boot 진입점, 설정, 정적 UI 화면, schema.sql
├── contract/     컴포넌트 간 계약(Contract API) 인터페이스
├── client-rest/  외부 REST 연동 어댑터
└── client-message/ 외부 MQ/EAI 연동 어댑터
```

---

## 아키텍처 혼용 원칙 (중요)

### 헥사고날 아키텍처 적용 도메인
- `paymeansregistration`, `realtimeregistration`, **`service`**
- 구조: `domain/model/` (순수 도메인) + `application/port/` (포트 인터페이스) + `infra/.../repository/` (포트 구현 어댑터)
- 유스케이스는 포트에만 의존. Mapper 직접 사용 금지.

### 계층형 아키텍처 적용 도메인
- `customer`, `billing`
- 구조: Controller → Service → Mapper (단순 CRUD라 포트/어댑터 불필요)
- Service가 MyBatis Mapper를 직접 주입받아 사용.

### 왜 혼용하나?
- 핵심 업무(납부수단/자동납부/서비스)는 규칙 복잡·외부연동 多 → 헥사고날
- 단순 기준정보(고객/청구계정)는 CRUD만 → 계층형으로 간결하게

---

## 도메인별 구현 현황 (autopay)

### 헥사고날 도메인

#### paymeansregistration (납부수단 등록/관리) — 완료
- `domain/model/`: PayMeans, AgentPayMap, PayMeansStatus, PayMeansType, AgentPayMapStatus 등
- `application/port/`: PayMeansRepositoryPort, AccountVaultPort, FinancialAuthGatewayPort, SmsAuthGatewayPort, RealtmAuthHistoryPort, AgentPayMapRepositoryPort
- `infra/vault/`: AccountVaultAdapter (AES 암복호, SHA-256 결정적 대체ID)
- `infra/persistence/`: PayMeansEntity, AgentPayMapEntity, BankacctCardInfoEntity, PayMeansRealtmAuthentcEntity + Mapper + RepositoryAdapter
- `rest/`: PayMeansCoreApiController, AgentPayMapController
- 주요 엔드포인트:
  - POST /api/autopay/pay-means (본인 등록)
  - POST /api/autopay/pay-means/agent-requests (대리인 등록)
  - POST /api/autopay/pay-means/{no}/agent-link (기존 수단 재사용)
  - POST /api/autopay/pay-means/{no}/suspend|resume|terminate (상태변경)
  - GET  /api/autopay/pay-means/agent-maps (수익자 기준 대리관계)
  - GET  /api/autopay/pay-means/agent-maps/as-owner (소유주 기준 대리관계)

#### service (이동전화 서비스) — 완료
- `domain/model/`: Service.java, ServiceStatus.java (enum: AC 사용중/SP 정지/TG 해지)
- `application/port/`: ServiceRepositoryPort (save/update/findById/findAll/findByBillingAccount/findByServiceNo)
- `infra/persistence/repository/`: ServiceRepositoryAdapter (포트 구현, 채번 1+9자리 담당)
- `infra/persistence/entity/`: ServiceEntity (@TableName("SERVICE"))
- `infra/persistence/mapper/`: ServiceMapper extends BaseMapper<ServiceEntity>
- `application/dto/`: RegisterServiceCommand, ServiceView
- `rest/`: ServiceController
- 주요 엔드포인트:
  - POST /api/autopay/services (등록)
  - GET  /api/autopay/services?tenantId=&billingAccountNo= (목록)
  - POST /api/autopay/services/{no}/suspend|resume|terminate (상태변경)
  - GET  /api/autopay/services/by-service-no?serviceNo= (서비스번호→고객 조회)

### 계층형 도메인

#### customer (고객) — 완료
- CustomerEntity, CustomerMapper, CustomerService, CustomerController
- /api/autopay/customers (등록/조회)
- /api/autopay/customers/by-rrn?rrn= (주민번호로 고객 조회 ← 화면에서 자주 사용)
- /api/autopay/customers/{no} (고객번호로 단건 조회)

#### billing (청구계정) — 완료
- BillingAccountEntity, BillingAccountMapper, BillingAccountService, BillingAccountController
- /api/autopay/billing-accounts (등록/목록)
- /api/autopay/billing-accounts?tenantId=&customerNo= (고객 기준 목록)

---

## 채번 규칙 (번호 자동생성)

| 엔티티 | 시작자리 | 자릿수 | 예시 |
|--------|---------|--------|------|
| 고객번호 (CUSTOMER_NO) | 9 | 10자리 | 9000000001 |
| 청구계정번호 (BILLING_ACCOUNT_NO) | 2 | 10자리 | 2000000001 |
| 납부계정번호 (PAY_ACCT_NO) | 3 | 10자리 | 3000000001 |
| 서비스관리번호 (SERVICE_MGMT_NO) | 1 | 10자리 | 1000000001 |

- 채번 방식: `"시작자리" + String.format("%09d", ThreadLocalRandom.current().nextInt(0, 1_000_000_000))`
- 헥사고날 도메인에서 채번은 **어댑터(인프라) 책임** (도메인 모델이 관여 안 함)
- 계층형 도메인에서 채번은 서비스 안에서 수행

---

## 상태코드 체계

### PayMeans 납부수단 상태 (PAY_MEANS_STAT_CD)
- `01` 등록중 (비대면 소유주 동의 대기)
- `10` 활성 (정상 사용)
- `20` 정지
- `30` 해지
- `40` 종료

### AgentPayMap 대리납부맵 상태 (AGENT_PAY_MAP_STAT_CD)
- `10` 요청 (소유주 동의 대기)
- `20` 활성
- `30` 거절
- `40` 중단
- `50` 종료
- **주의**: PayMeans와 코드값이 겹침 (10/20/30). 각각 의미가 다름.

### ServiceStatus 서비스 상태 (SERVICE_STAT_CD)
- `AC` 사용중
- `SP` 정지
- `TG` 해지

### 실시간인증 결과코드
- AUTHENTC_RESULT_CD: 2자리 (00 성공 / 99 실패)
- EXTNL_AUTHENTC_RESULT_CD: 4자리 (0000 성공)

---

## DB 테이블 목록 (nova_db)

| # | 테이블 | 설명 |
|---|--------|------|
| 1 | PAY_MEANS | 납부수단 |
| 2 | PAY_ACCT | 납부계정 |
| 3 | PAY_ACCT_RCPTN | 납부계정접수 |
| 4 | PAY_ACCT_AUTHENTC | 납부계정인증 |
| 5 | PAY_ACCT_AUTHENTC_WTT | 납부계정인증(WTT) |
| 6 | PAY_MEANS_REALTM_AUTHENTC | 납부수단실시간인증 |
| 7 | EXTERNAL_INST_AUTO_PAY_APPREQ | 외부기관자동납부신청 |
| 8 | EXTERNAL_INST_AUTO_PAY_APPREQ_DTL | 외부기관자동납부신청상세 |
| 9 | PAY_AGENT_MAP | 대리납부 맵핑 |
| 10 | CUSTOMER | 고객 |
| 11 | BILLING_ACCOUNT | 청구계정 |
| 12 | BANKACCT_CARD_INFO | 계좌카드정보 금고 (AES 암호화) |
| 13 | SERVICE | 서비스(이동전화) |

- DDL 위치: `boot/src/main/resources/schema.sql`
- DBeaver로 직접 실행해서 테이블 생성 (스프링 자동 실행 아님)

---

## 계좌/카드 금고 (AccountVault) 설계

- **원칙**: 실제 계좌/카드번호는 DB에 평문 저장 안 함. 암호화해서 BANKACCT_CARD_INFO에 보관, PAY_MEANS에는 대체ID만 저장.
- **암호화**: AES-128, 키는 `${autopay.vault.aes-key:autopay-demo-key-1234}`
- **대체ID**: 원본번호의 SHA-256 해시 앞 7바이트 → 양수 Long (결정적 = 같은 번호는 항상 같은 대체ID)
- **복호화**: `AccountVaultPort.resolve(altrnateId)` → 원본 복원 가능 (재인증 시 사용)
- **마스킹**: 조회 시 `PayMeansQueryService`가 금고에서 복호화 후 앞4+뒤4 마스킹 (`accountOrCardNoMasked` 필드로 View에 포함)

---

## 화면 구성 (boot/src/main/resources/static/)

| 파일 | 기능 |
|------|------|
| index.html | 좌측 메뉴 + iframe 셸 |
| customer.html | 고객 등록 |
| billing-account.html | 청구계정 등록 |
| service.html | 서비스(이동전화) 등록/조회/상태변경 |
| pay-means.html | 납부수단 등록 (본인/대리인) |
| agent-link.html | 기존 수단 대리납부 연결 |
| pay-means-hub.html | 등록+연결 통합 탭 화면 |
| pay-means-view.html | 납부수단 조회 (지갑/대리관계/상태변경) |
| agent-consent.html | 대리납부 동의 (승인/거절) |
| payment-account.html | 납부계정 등록 |

### 정적 리소스 개발 설정 (application.yml)
```yaml
spring.web.resources.static-locations:
  - file:./boot/src/main/resources/static/
  - file:./src/main/resources/static/
  - file:c:/nova/autopay/boot/src/main/resources/static/
  - classpath:/static/
```
→ 앱 실행 중 HTML 수정 시 브라우저 새로고침만으로 반영 (빌드 불필요)

---

## 공통 UI 패턴 (화면 조회 라인)

모든 화면에서 고객 조회 라인은 통일된 패턴 사용:

```html
<!-- [주민번호 ▾ 콤보] [입력란] [조회 버튼] 한 라인 -->
<div class="row" style="align-items:center;">
    <div style="flex:0 0 auto;">
        <select id="lookupType" class="lookup-type" onchange="onLookupTypeChange()">
            <option value="RRN">주민번호</option>
            <option value="SVC">서비스번호</option>
        </select>
    </div>
    <div style="flex:1;"><input id="ownerKey" placeholder="..."></div>
    <div style="flex:0 0 auto;"><button class="lookup" onclick="lookupOwner()">조회</button></div>
</div>
```

- 조회구분 콤보 스타일: `select.lookup-type` (연회색 배경 #f8fafc, 옅은 테두리)
- 주민번호 → `GET /api/autopay/customers/by-rrn?rrn=`
- 서비스번호 → `GET /api/autopay/services/by-service-no?serviceNo=`
- 두 API 모두 `CustomerView` (customerNo, customerNm, mobilePhno) 반환

### 관계구분코드 (relCatgCd)
| 코드 | 의미 |
|------|------|
| SELF | 본인 |
| 01 | 부모 |
| 02 | 배우자 |
| 03 | 자녀 |
| 09 | 기타 |

### 테넌트ID
- 모든 화면에서 hidden input으로 "SKT" 고정. 사용자에게 노출 안 함.
- `<input type="hidden" id="tenantId" value="SKT">`

---

## 공통 개발 규칙

### 등록자/변경자 자동 채움
- `MybatisPlusConfig`의 MetaObjectHandler가 INSERT/UPDATE 시 자동 채움
- FIRST_REGISTR_ID, FINAL_CHGR_ID: 항상 `P215138`
- FIRST_REGIST_DTM, FINAL_CHG_DTM: `LocalDateTime.now()`
- 서비스 코드에서 직접 세팅 불필요

### 예외 처리 (GlobalExceptionHandler)
- `IllegalStateException` → HTTP 400 + `{"success":false,"message":"..."}`
- `IllegalArgumentException` → HTTP 400 + `{"success":false,"message":"..."}`
- 도메인이 상태 규칙 위반 시 IllegalStateException, 데이터 없음 시 IllegalArgumentException 던짐
- 화면 JS: `res.ok`가 false이면 `data.message`를 `alert()`으로 표시

### auth-first 원칙 (납부수단 등록)
- 금융기관 실시간인증을 먼저 수행하고 성공 시에만 저장
- 실패해도 인증이력(PAY_MEANS_REALTM_AUTHENTC)은 남김
- `RealtmAuthHistoryRepositoryAdapter.createRequested/applyResult`에 `@Transactional(REQUIRES_NEW)`

### 실시간인증 스텁
- `FinancialAuthStubAdapter`: 항상 성공 반환 (개발환경)
- 실제 금융기관 연동 시 client-rest의 `FinancialAuthRestAdapter`로 교체

---

## DOC 문서 위치 (학습/설계 문서)

| 파일 | 내용 |
|------|------|
| `c:\nova\DOC\청구계정등록-화면부터DB까지.md` | 계층형 아키텍처 흐름 설명 (초보자용) |
| `c:\nova\DOC\서비스등록-화면부터DB까지.md` | 헥사고날 아키텍처 흐름 + 포트·어댑터 연결 원리 설명 |
| `c:\nova\DOC\autopay-트리구조.md` | autopay 전체 프로젝트 파일 트리 |
| `c:\nova\DOC\autopay-select-쿼리-DBeaver.md` | DBeaver 실행용 SELECT 쿼리 모음 |
| `n:\개인\nova\DOC\` | 동일 문서 별도 보관 경로 |

---

## 납부수단 등록 정책 요약

### 본인 등록 (registerByOwner)
- 인증 성공 → 활성(10) 즉시

### 대리인 등록 (registerByAgent)
- 대면: 인증 성공 → 활성(10) + AgentPayMap 활성
- 비대면: 인증 성공 → 등록중(01) + AgentPayMap 요청(10) + 소유주 SMS 동의요청

### 기존 수단 재사용 (linkExistingMeansToAgent)
- 부모(소유주)의 기존 활성 수단을 자식(수익자)이 사용
- 새 PayMeans 생성 안 함, AgentPayMap만 추가
- 금고에서 원본 복호화 → 매번 재인증 → 소유주 SMS 동의요청
- 대리납부 동의 시: PayMeans가 이미 활성(10)이면 상태 전이 생략, AgentPayMap만 활성화

### 중복 체크 기준
- 기준1: 동일 대리납부자↔피대리납부자 관계 중복 불가
- 기준3: 동일 계좌/카드 대체ID 1건만 (본인/대리 무관)
- 재사용 연결 시: 기준1만 적용, 기준3 미적용
