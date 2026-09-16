# 서비스(이동전화) 등록: 화면부터 DB까지 전체 여정 (헥사고날)

> 대상: 자바/스프링 초보자
> 기준 코드: `c:\nova\autopay` (service 도메인) — 실제 소스 기준
> 특징: 이 도메인은 **헥사고날 아키텍처**(포트 & 어댑터)로 만들어졌다.
> 비교: 청구계정(billing)은 계층형이라 Service가 Mapper를 직접 썼지만,
>       서비스(service)는 그 사이에 "포트(문)"를 두어 기술과 업무를 분리했다.

---

## 0. 큰 그림

서비스 하나를 등록할 때 데이터는 이 순서로 흐른다.
청구계정과 다른 점은 Service와 DB(Mapper) 사이에 **포트(인터페이스)와 어댑터(구현)** 가 끼어 있다는 것.

```
[브라우저 화면]  service.html
      │  ① 고객→청구계정 선택 후 "서비스 등록" 클릭 → JSON 전송(POST)
      ▼
[Controller]  ServiceController               ← 접수 창구
      │  ② JSON을 Command로 변환해 유스케이스 호출
      ▼
[Application]  ServiceRegistrationService      ← 업무 흐름(유스케이스)
      │  ③ 도메인 모델 생성 + 저장을 "포트"에 요청 (기술은 모름)
      ▼
[Port]  ServiceRepositoryPort (인터페이스)      ← 업무와 기술 사이의 "문"
      │  ④ 실제 구현체가 대신 처리
      ▼
[Adapter]  ServiceRepositoryAdapter            ← 포트 구현(기술 담당)
      │  ⑤ 채번 + 도메인→엔티티 변환 후 Mapper 호출
      ▼
[Mapper]  ServiceMapper → [MySQL] SERVICE 테이블에 INSERT
```

관련 파일 위치:

| 역할 | 파일 |
|------|------|
| 화면 | `boot/.../static/service.html` |
| Controller | `app/.../service/rest/ServiceController.java` |
| Command(입력 상자) | `app/.../service/application/dto/RegisterServiceCommand.java` |
| Application(유스케이스) | `app/.../service/application/ServiceRegistrationService.java` |
| **도메인 모델** | `app/.../service/domain/model/Service.java`, `ServiceStatus.java` |
| **포트(인터페이스)** | `app/.../service/application/port/ServiceRepositoryPort.java` |
| **어댑터(구현)** | `app/.../service/infra/persistence/repository/ServiceRepositoryAdapter.java` |
| Entity(DB 상자) | `app/.../service/infra/persistence/entity/ServiceEntity.java` |
| Mapper(DB 통역) | `app/.../service/infra/persistence/mapper/ServiceMapper.java` |
| View(출력 상자) | `app/.../service/application/dto/ServiceView.java` |
| 테이블 | `boot/.../schema.sql` → `SERVICE` |

---

## 헥사고날을 한 문장으로

> **업무 로직(유스케이스·도메인)은 "무엇을 할지"만 알고, "어떻게 저장할지(MySQL 등 기술)"는 모른다.**
> 그 사이를 **포트(인터페이스)** 라는 문으로 연결하고, 실제 기술은 **어댑터**가 그 문을 구현한다.

왜 이렇게? 나중에 저장 기술을 바꿔도(예: MyBatis → 다른 DB) **업무 로직은 안 건드려도** 되기 때문. 문(포트)은 그대로 두고 어댑터만 갈아끼우면 된다.

---

## ① 화면 (service.html)

고객을 조회하고, 그 고객의 청구계정을 콤보에서 고른 뒤, 서비스번호를 입력하고 "서비스 등록"을 누른다.

```javascript
const body = {
    tenantId: val('tenantId'),                 // "SKT"
    billingAccountNo: val('billingAccountNo'), // 선택한 청구계정번호
    serviceNoAltrnateId: val('serviceNoAltrnateId')  // 서비스번호(전화번호 등)
};
const res = await fetch('/api/autopay/services', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
});
```

핵심: 주소 `/api/autopay/services` + `POST` + JSON 3개 값.
청구계정번호는 화면이 보내지만(청구계정과 연동), **서비스관리번호는 서버가 채번**하므로 안 보낸다.

---

## ② Controller — 접수 창구

```java
@RestController
@RequestMapping("/api/autopay/services")
public class ServiceController {

    private final ServiceRegistrationService serviceService;   // 유스케이스 주입

    @PostMapping
    public ServiceView register(@RequestBody RegisterServiceCommand command) {
        return serviceService.register(command);   // 업무는 유스케이스에 위임
    }
}
```

청구계정 컨트롤러와 똑같은 틀이다.
- `@PostMapping` : 화면이 POST로 보냈으니 이 메서드 실행
- `@RequestBody` : JSON → `RegisterServiceCommand`(입력 상자)로 자동 변환
- 컨트롤러는 일을 직접 안 하고 유스케이스에 넘기고 결과를 돌려준다.

---

## ③ Application — 유스케이스 (업무 흐름)

여기가 헥사고날의 핵심이다. **Mapper가 없다.** 대신 **포트**와 **도메인 모델**만 쓴다.

```java
@org.springframework.stereotype.Service
public class ServiceRegistrationService {

    private final ServiceRepositoryPort serviceRepository;   // ← 포트(인터페이스)에만 의존!
    // (MyBatis Mapper를 직접 알지 못한다)

    @Transactional
    public ServiceView register(RegisterServiceCommand cmd) {
        // 1) 도메인 모델 생성 (상태는 도메인이 스스로 '사용중'으로 정함)
        Service service = Service.register(
                cmd.tenantId(), cmd.billingAccountNo(), cmd.serviceNoAltrnateId());
        // 2) 저장은 '포트'에 요청 (어떻게 저장하는지는 모름)
        service = serviceRepository.save(service);
        // 3) 결과를 화면용 상자(View)로 변환해 반환
        return toView(service);
    }
}
```

포인트:
- `serviceRepository`의 타입은 **인터페이스(`ServiceRepositoryPort`)** 다. 구현체가 뭔지 유스케이스는 모른다. 스프링이 실행 시 어댑터를 끼워준다.
- 저장 로직(SQL, 채번)이 여기 없다. `serviceRepository.save(...)`라고 "부탁"할 뿐.
- 상태(AC 사용중)도 서비스가 직접 문자열로 넣지 않는다. **도메인 모델이 정한다.**

---

## 도메인 모델 — 업무 규칙의 주인 (Service / ServiceStatus)

청구계정에는 없던 것. 서비스는 "상태"와 "상태 바꾸는 규칙"을 **도메인 모델이 직접 소유**한다.

```java
public class Service {
    private ServiceStatus status;   // AC 사용중 / SP 정지 / TG 해지

    // 등록하면 무조건 '사용중'으로 시작
    public static Service register(String tenantId, String billingAccountNo, String serviceNoAltrnateId) {
        Service s = new Service();
        s.tenantId = tenantId;
        s.billingAccountNo = billingAccountNo;
        s.serviceNoAltrnateId = serviceNoAltrnateId;
        s.status = ServiceStatus.ACTIVE;   // 규칙: 신규는 사용중
        return s;
    }

    public void suspend() {   // 정지: 사용중일 때만 가능
        requireStatus(ServiceStatus.ACTIVE, "사용중 상태만 정지할 수 있습니다.");
        this.status = ServiceStatus.SUSPENDED;
    }
    public void resume() {    // 재개: 정지일 때만 가능
        requireStatus(ServiceStatus.SUSPENDED, "정지 상태만 재개할 수 있습니다.");
        this.status = ServiceStatus.ACTIVE;
    }
    public void terminate() { // 해지: 사용중/정지에서 가능
        if (status != ServiceStatus.ACTIVE && status != ServiceStatus.SUSPENDED)
            throw new IllegalStateException("해지 불가 상태입니다");
        this.status = ServiceStatus.TERMINATED;
    }
}
```

왜 이렇게 하나? "정지된 걸 또 정지"하거나 "해지된 걸 재개"하는 **잘못된 상태 변경을 도메인이 스스로 막는다.** 규칙이 한곳(도메인)에 모여 있어 안전하고, 화면이나 서비스가 실수로 이상한 상태를 못 만든다.

`ServiceStatus`는 코드(AC/SP/TG)와 한글명(사용중/정지/해지)을 짝지어 관리하는 enum이다.

---

## ④ 포트 — 업무와 기술 사이의 문 (ServiceRepositoryPort)

```java
public interface ServiceRepositoryPort {
    Service save(Service service);                 // 저장하고 채번된 것 반환
    void update(Service service);                  // 상태 변경 반영
    Optional<Service> findById(String serviceMgmtNo);
    List<Service> findAll();
    List<Service> findByBillingAccount(String tenantId, String billingAccountNo);
    Optional<Service> findByServiceNo(String serviceNoAltrnateId);
}
```

이건 **인터페이스(약속)** 일 뿐, 실제 코드가 없다. "이런 기능이 있다"는 명세만 있다.
유스케이스는 이 약속만 보고 일한다. MySQL이든 뭐든 신경 안 쓴다.

> 비유: 유스케이스는 "택배 보내줘"(save)라고 문 앞에 두면 되고, 누가 어떻게 배송하는지는 택배사(어댑터)가 알아서 한다.

---

## ⑤ 어댑터 — 포트의 실제 구현 (ServiceRepositoryAdapter)

포트를 구현하면서 진짜 기술(MyBatis, 채번, 변환)을 담당한다.

```java
@Repository
public class ServiceRepositoryAdapter implements ServiceRepositoryPort {

    private final ServiceMapper serviceMapper;   // 여기서만 MyBatis를 안다

    @Override
    public Service save(Service service) {
        // 채번: 1로 시작하는 10자리 (인프라 책임)
        if (service.getServiceMgmtNo() == null) {
            service.assignServiceMgmtNo(generateServiceMgmtNo());
        }
        serviceMapper.insert(toEntity(service));   // 도메인 → 엔티티 변환 후 저장
        return service;
    }

    // 도메인 모델 → DB 엔티티
    private ServiceEntity toEntity(Service s) {
        ServiceEntity e = new ServiceEntity();
        e.setServiceMgmtNo(s.getServiceMgmtNo());
        e.setTenantId(s.getTenantId());
        e.setBillingAccountNo(s.getBillingAccountNo());
        e.setServiceNoAltrnateId(s.getServiceNoAltrnateId());
        e.setServiceStatCd(s.getStatus().code());   // AC/SP/TG 문자열로
        return e;
    }
    // DB 엔티티 → 도메인 모델 (조회 시)
    // private Service toDomain(ServiceEntity e) { ... ServiceStatus.fromCode(...) ... }
}
```

포인트:
- `@Repository`로 등록되어, 스프링이 유스케이스의 포트 자리에 **이 어댑터를 자동으로 끼워준다.**
- 채번(1+9자리)과 도메인↔엔티티 변환처럼 "기술적인 일"이 전부 여기 모여 있다.
- 유스케이스는 이 파일의 존재조차 몰라도 된다. 포트만 보고 일하니까.

---

## Entity / Mapper — 청구계정과 동일

```java
@TableName("SERVICE")
public class ServiceEntity { ... @TableId(type = IdType.INPUT) ... fill = INSERT ... }

@Mapper
public interface ServiceMapper extends BaseMapper<ServiceEntity> { }
```

- Entity: SERVICE 테이블과 1:1 매핑. 등록일시/등록자(P215138)는 `fill = INSERT`로 자동 채움.
- Mapper: 비어 있어도 `BaseMapper` 덕에 insert/selectList 등 자동 제공.

`serviceMapper.insert(e)` 실행 시:
```sql
INSERT INTO SERVICE
  (SERVICE_MGMT_NO, TENANT_ID, BILLING_ACCOUNT_NO, SERVICE_NO_ALTRNATE_ID,
   SERVICE_STAT_CD, FIRST_REGIST_DTM, FIRST_REGISTR_ID, ...)
VALUES
  ('1000000123', 'SKT', '2000000001', '01012345678', 'AC', '2026-...', 'P215138', ...);
```

---

## ⑥ 결과 되돌아가기 (View)

```java
private ServiceView toView(Service s) {
    return new ServiceView(
        s.getServiceMgmtNo(),           // 채번된 번호
        s.getTenantId(),
        s.getBillingAccountNo(),
        s.getServiceNoAltrnateId(),
        s.getStatus().code(),           // "AC"
        s.getStatus().label());         // "사용중"
}
```

컨트롤러가 View를 반환하면 스프링이 JSON으로 바꿔 화면에 응답 → 화면은 "등록 성공, 상태 AC(사용중)"을 표시한다.

---

## 전체 흐름 한 번에

```
브라우저: "서비스 등록" 클릭
   │  POST /api/autopay/services + JSON{tenantId, billingAccountNo, serviceNoAltrnateId}
   ▼
Controller.register(@RequestBody Command)         JSON → Command 변환
   ▼
ServiceRegistrationService.register(Command)      [유스케이스]
   │  1. Service.register(...)  → 도메인 모델 생성 (상태 AC)
   │  2. serviceRepository.save(service)  ← 포트에 저장 요청
   ▼
ServiceRepositoryPort.save(...)                   [포트=문, 인터페이스]
   ▼
ServiceRepositoryAdapter.save(...)                [어댑터=기술]
   │  3. 채번(1+9자리) → 도메인→엔티티 변환
   │  4. serviceMapper.insert(entity)
   ▼
ServiceMapper → MySQL: SERVICE 에 INSERT
   │  (MetaObjectHandler가 등록일시/등록자 P215138 자동 채움)
   │
   ▼ (역방향)
어댑터 → 유스케이스: 채번된 도메인 반환
유스케이스: 도메인 → View 변환
Controller: View 반환 → JSON
브라우저: "등록 성공" 표시
```

---

## 청구계정(계층형) vs 서비스(헥사고날) 비교

| 구분 | 청구계정 (계층형) | 서비스 (헥사고날) |
|------|-------------------|-------------------|
| 저장 접근 | Service가 **Mapper 직접** 사용 | Service가 **포트**만 사용 (Mapper 모름) |
| 포트/어댑터 | 없음 | `ServiceRepositoryPort` + `ServiceRepositoryAdapter` |
| 도메인 모델 | 없음 (Entity를 바로 다룸) | `Service`/`ServiceStatus` (상태 규칙 소유) |
| 상태 규칙 | 서비스 코드에 흩어짐 | 도메인 모델이 스스로 검증 |
| 채번 위치 | 서비스 안 | 어댑터(인프라) |
| 장점 | 단순, 파일 적음 | 기술 교체에 강함, 규칙 안전 |
| 적합한 곳 | 단순 CRUD | 규칙 복잡·외부연동 많은 핵심 업무 |

둘 다 정답이다. **단순하면 계층형, 복잡하고 오래 유지할 핵심이면 헥사고날.** 우리 프로젝트는 핵심(납부수단/자동납부/서비스)은 헥사고날, 단순 기준정보(고객/청구계정)는 계층형으로 섞어 쓴다.

---

## 용어 미니 사전

| 용어 | 초보자용 설명 |
|------|---------------|
| 헥사고날 | 업무 로직을 가운데 두고, 기술(DB/외부연동)을 "포트+어댑터"로 바깥에 두는 구조 |
| 포트(Port) | 업무와 기술을 잇는 "약속(인터페이스)". 기능 명세만 있고 구현은 없음 |
| 어댑터(Adapter) | 포트를 실제로 구현한 것. 진짜 기술(MyBatis 등)이 여기 있음 |
| 도메인 모델 | 업무 규칙(상태 전이 등)을 담은 순수 자바 객체. 프레임워크 비의존 |
| 의존성 역전(DIP) | 업무가 기술에 의존하지 않고, 기술이 업무가 정한 포트를 따르게 뒤집는 것 |
| 유스케이스 | 하나의 업무 시나리오(예: 서비스 등록)를 수행하는 application 서비스 |
