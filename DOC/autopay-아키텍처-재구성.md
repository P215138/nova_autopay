# autopay 아키텍처 재구성 결과

> 근거: Backend Architecture 구조 간소화 회의 (2026-09-05 리뷰, 문서 v1.1 2026-09-10)
> 적용: `c:\nova\autopay`

---

## 1. 배경

초기 autopay는 헥사고날 아키텍처를 기술 계층별로 잘게 나눈 8개 모듈 구조였다.
회의 결과, 모듈이 지나치게 세분화되어 관리 비용이 크다고 판단하여 **비즈니스 모듈 하위 패키지 기준으로 통합**하기로 결정했다.

핵심 결정: `core + orchestration + presentation + external` → **`app` 하나로 통합**.

---

## 2. As-Is → To-Be

### As-Is (8모듈)
```
contract, core, orchestration, presentation,
external, client-rest, client-message, boot
```

### To-Be (5모듈)
```
contract, app, client-rest, client-message, boot
```

### 매핑

| As-Is 모듈 | To-Be 모듈 | 처리 |
|-----------|-----------|------|
| core | **app** | 통합 |
| orchestration | **app** | 통합 |
| presentation | **app** | 통합 |
| external | **app** | 통합 (본 프로젝트에선 app 내 `adapter/out` 스텁으로 이미 존재) |
| contract | contract | 유지 |
| client-rest | client-rest | 유지 (REST 외부연동) |
| client-message | client-message | 유지 (MQ/EAI 외부연동) |
| boot | boot | 유지 |

---

## 3. 최종 모듈 구조

```
autopay/
├── settings.gradle          (include: contract, app, client-rest, client-message, boot)
├── build.gradle             (루트: Java 23, Spring Boot 3.3.0 BOM, MyBatis-Plus 3.5.7)
├── contract/                모듈 간 공개 계약 (Contract API, DTO)
├── app/                     ★ core+orchestration+presentation 통합 (약 283 소스)
│   └── src/main/java/com/skt/autopay/
│       ├── paymeansregistration/   납부수단관리 BM
│       │   ├── domain/model/       도메인 + 상태 enum
│       │   ├── application/        command / query / dto / port
│       │   ├── adapter/out/        스텁 어댑터 (금융인증, SMS)
│       │   ├── infra/persistence/  entity / mapper / repository
│       │   └── rest/openapi/...    REST 컨트롤러
│       ├── realtimeregistration/   실시간 신청/변경/해지 BM
│       └── orchestration/          H109 통합인증 오케스트레이션
├── client-rest/             REST 외부연동 어댑터
├── client-message/          MQ/EAI 외부연동 어댑터 (기존 client)
└── boot/                    실행 애플리케이션 (조립 + 설정 + application.yml + schema.sql)
```

---

## 4. 모듈 의존 관계

```
        ┌─────────── boot ───────────┐   (전부 조립 + 실행)
        │        │         │         │
        ▼        ▼         ▼         ▼
   client-rest  client-message      app
        │        │                   │
        └────────┴───────┬───────────┘
                         ▼
                     contract
```

- **단방향 의존**: boot → (client-rest, client-message, app) → contract
- `app`이 핵심 로직 전체를 담고, `contract`만 참조
- `client-rest`/`client-message`는 외부연동 어댑터로 `app`과 `contract` 참조
- `boot`가 최상위에서 모든 모듈을 조립하여 실행

---

## 5. 재구성 시 유의점 (실제로 적용한 것)

- **package는 변경 없음**: 모든 소스가 `com.skt.autopay.*` 기준이라, 모듈(폴더) 이동만으로 import 변화 없음.
- 소스는 물리적 소속 Gradle 모듈만 바뀜.
- `app/build.gradle`이 기존 core 의존성(MyBatis-Plus, web, validation, jdbc, mysql) 흡수.
- `AutopayApplication`의 `scanBasePackages = "com.skt.autopay"`, `@MapperScan("com.skt.autopay.**.infra.persistence.mapper")` 덕분에 통합 후에도 빈/매퍼 스캔이 그대로 동작.

---

## 6. 검증

- `gradlew clean build -x test` 성공
- `gradlew :boot:bootJar` 성공 (실행 가능 아티팩트 생성)

---

## 7. 남은 항목 / TODO

- [ ] 회의 문서 하단 BOM · `nova-bil-payment-management` 패키지 명명 규칙(cardcollection/paymentrequest) 세부 반영
- [ ] client-rest 실제 어댑터 구현 (본 재구성에 이어 추가)
- [ ] app 내 비즈니스 모듈별 패키지 경계 가이드 문서화
