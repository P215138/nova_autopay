# 청구계정 등록: 화면부터 DB까지 전체 여정

> 대상: 자바/스프링 초보자
> 기준 코드: `c:\nova\autopay` (billing 도메인) — 실제 소스 기준으로 작성
> 목적: 화면 클릭 한 번이 어떤 경로로 DB에 저장되는지, 각 클래스가 왜 나뉘어 있는지 이해

---

## 0. 큰 그림

청구계정 하나를 등록할 때 데이터는 이 순서로 흐른다.

```
[브라우저 화면]  billing-account.html
      │  ① 입력 후 "등록" 클릭 → fetch로 JSON 전송
      ▼
[Controller]  BillingAccountController      ← 접수 창구
      │  ② JSON을 Command 객체로 변환해서 서비스에 넘김
      ▼
[Service]  BillingAccountService            ← 실제 업무(채번, 값 채우기)
      │  ③ 번호 채번 + 값 세팅 → Mapper에 저장 요청
      ▼
[Mapper]  BillingAccountMapper              ← DB와 대화하는 통역사
      │  ④ INSERT SQL 실행
      ▼
[MySQL]  BILLING_ACCOUNT 테이블             ← 최종 저장소
```

관련 파일 위치:

| 역할 | 파일 |
|------|------|
| 화면 | `boot/src/main/resources/static/billing-account.html` |
| Controller | `app/.../billing/rest/BillingAccountController.java` |
| Command(입력 상자) | `app/.../billing/application/dto/RegisterBillingAccountCommand.java` |
| Service(업무) | `app/.../billing/application/BillingAccountService.java` |
| Entity(DB 상자) | `app/.../billing/infra/persistence/entity/BillingAccountEntity.java` |
| Mapper(DB 통역) | `app/.../billing/infra/persistence/mapper/BillingAccountMapper.java` |
| View(출력 상자) | `app/.../billing/application/dto/BillingAccountView.java` |
| 테이블 | `boot/src/main/resources/schema.sql` → `BILLING_ACCOUNT` |

---

## ① 화면 (billing-account.html)

사용자가 주민번호로 고객을 조회하고, 납부구분/청구주기를 골라 "등록"을 누르면 자바스크립트가 서버로 요청을 보낸다.

```javascript
const res = await fetch('/api/autopay/billing-accounts', {   // 이 주소로
    method: 'POST',                                          // POST 방식
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({                                   // 이 데이터를 JSON으로
        tenantId: val('tenantId'),        // "SKT" (화면에선 숨김, 내부 고정)
        customerNo: customerNo,           // "9000000001"
        payClCode: ...,
        billingCycleCode: ...
    })
});
```

핵심 3가지가 "요청이 서버 어디로 갈지"를 결정한다.
- 주소: `/api/autopay/billing-accounts`
- 방식: `POST`
- 본문: JSON 데이터

---

## ② Controller — 접수 창구

```java
@RestController                                       // REST 요청을 받는 창구
@RequestMapping("/api/autopay/billing-accounts")      // 이 주소 담당
public class BillingAccountController {

    private final BillingAccountService billingAccountService;

    public BillingAccountController(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;   // 스프링이 서비스를 자동 주입
    }

    @PostMapping                                      // POST 요청 → 이 메서드
    public BillingAccountView register(@RequestBody RegisterBillingAccountCommand command) {
        return billingAccountService.register(command);   // 업무는 서비스에 위임
    }
}
```

포인트:
- `@RestController`: 이 클래스가 웹 요청 창구임을 스프링에 표시.
- `@RequestMapping(...)`: 화면이 보낸 주소와 연결.
- `@PostMapping`: 화면이 POST로 보냈으니 이 메서드 실행 (조회는 `@GetMapping`).
- `@RequestBody`: 화면이 보낸 JSON을 자바 객체(Command)로 자동 변환. JSON의 `tenantId`, `customerNo`가 같은 이름 필드로 들어감.

컨트롤러는 일을 직접 하지 않고 받아서 넘기고 결과를 돌려주는 창구 역할만 한다. 그래서 화면이 바뀌어도 업무 로직은 안 건드린다.

---

## Command란? (입력 데이터 상자 = DTO)

```java
public record RegisterBillingAccountCommand(
        String tenantId,
        String customerNo,
        String payClCode,
        String billingCycleCode
) {
}
```

- `record`는 자바에서 값만 담는 간단한 상자를 만드는 문법.
- 화면이 보낸 4개 값을 담아 서비스로 전달한다.
- 화면에서 오는 "입력값"과 DB에 저장되는 "실제 데이터"는 형태가 다르므로(청구계정번호는 서버가 만든다), 입력용(Command)·저장용(Entity)·출력용(View) 상자를 분리한다.

---

## ③ Service — 실제 업무를 하는 곳

```java
@Service
public class BillingAccountService {

    private final BillingAccountMapper billingAccountMapper;   // DB 담당

    public BillingAccountService(BillingAccountMapper billingAccountMapper) {
        this.billingAccountMapper = billingAccountMapper;
    }

    @Transactional                              // 하나의 거래(트랜잭션)로 처리
    public BillingAccountView register(RegisterBillingAccountCommand cmd) {
        BillingAccountEntity e = new BillingAccountEntity();   // DB 저장용 빈 상자
        e.setBillingAccountNo(generateBillingAccountNo());     // 청구계정번호 채번(서버 생성)
        e.setTenantId(cmd.tenantId());                         // 화면값 복사
        e.setCustomerNo(cmd.customerNo());
        e.setPayClCode(cmd.payClCode());
        e.setBillingCycleCode(cmd.billingCycleCode());
        e.setUseYn("Y");                                       // 신규는 사용중(Y) 고정
        billingAccountMapper.insert(e);                        // DB에 저장 지시
        return toView(e);                                      // 결과를 출력 상자로 변환해 반환
    }

    /** 2로 시작하는 10자리 청구계정번호 채번 (뒤 9자리 랜덤) */
    private String generateBillingAccountNo() {
        int rest = ThreadLocalRandom.current().nextInt(0, 1_000_000_000);
        return "2" + String.format("%09d", rest);   // "2" + 9자리 = 10자리
    }
}
```

여기에 핵심 업무 로직이 있다.
- 채번: 화면은 청구계정번호를 안 보낸다. 서버가 "2로 시작 10자리" 규칙으로 만든다.
- `@Transactional`: 메서드 안 DB 작업은 전부 성공하거나 전부 취소(롤백). 여러 테이블 저장 시 특히 중요.
- Command → Entity 변환: 화면값을 저장용 상자에 옮기고, 서버가 만든 값(번호, useYn)을 추가.

---

## Entity란? (DB 테이블과 1:1 상자)

```java
@TableName("BILLING_ACCOUNT")                  // 이 클래스 = BILLING_ACCOUNT 테이블
public class BillingAccountEntity {

    @TableId(value = "BILLING_ACCOUNT_NO", type = IdType.INPUT)   // PK, 직접 입력
    private String billingAccountNo;

    @TableField("TENANT_ID")                   // 필드 = 컬럼 매핑
    private String tenantId;
    // ... 나머지 컬럼 ...

    @TableField(value = "FIRST_REGIST_DTM", fill = FieldFill.INSERT)   // 저장 시 자동 채움
    private LocalDateTime firstRegistDtm;

    @TableField(value = "FIRST_REGISTR_ID", fill = FieldFill.INSERT)
    private String firstRegistrId;
}
```

- `@TableName`: 어느 테이블인지.
- `@TableId(type = IdType.INPUT)`: PK를 우리가 직접 넣는다는 뜻(그래서 서비스에서 채번). DB 자동증가 아님.
- `@TableField("...")`: 자바 필드와 DB 컬럼 대응.
- `fill = FieldFill.INSERT`: 등록일시/등록자ID를 서비스에서 세팅하지 않아도 저장 시 자동으로 채워진다.

### 자동 채움은 누가? (MetaObjectHandler)

등록자ID(`firstRegistrId`)를 서비스에서 세팅하지 않았는데 DB에는 `P215138`이 들어간다. `MybatisPlusConfig`에 등록된 자동 채움 담당자가 INSERT 직전에 `fill = FieldFill.INSERT` 필드를 채우기 때문이다. 덕분에 모든 화면에서 등록일시/등록자를 매번 세팅하는 중복을 없앴다.

---

## ④ Mapper — DB와 대화하는 통역사

```java
@Mapper
public interface BillingAccountMapper extends BaseMapper<BillingAccountEntity> {
}
```

내용이 비어 있는데도 `insert`가 동작한다.
- `BaseMapper<Entity>`를 상속하면 MyBatis-Plus가 `insert / selectList / selectById / deleteById` 등 기본 CRUD를 자동 제공.
- Entity의 `@TableName`, `@TableField` 정보로 라이브러리가 SQL을 자동 생성. `INSERT INTO ...`를 직접 안 써도 된다.

`billingAccountMapper.insert(e)`가 실행되면 대략 이런 SQL이 나간다.

```sql
INSERT INTO BILLING_ACCOUNT
  (BILLING_ACCOUNT_NO, TENANT_ID, CUSTOMER_NO, PAY_CL_CODE,
   BILLING_CYCLE_CODE, USE_YN, FIRST_REGIST_DTM, FIRST_REGISTR_ID, ...)
VALUES
  ('2000000123', 'SKT', '9000000001', ..., 'Y', '2026-09-05 ...', 'P215138', ...);
```

---

## ⑤ 결과 되돌아가기 (View)

저장 후 서비스는 결과를 출력용 상자(View)로 바꿔 반환한다.

```java
private BillingAccountView toView(BillingAccountEntity e) {
    return new BillingAccountView(
        e.getBillingAccountNo(),   // 방금 채번된 번호
        e.getTenantId(),
        e.getCustomerNo(),
        e.getPayClCode(),
        e.getBillingCycleCode(),
        e.getUseYn());
}
```

컨트롤러가 이 View를 반환하면 스프링이 자동으로 JSON으로 변환해 화면에 응답한다. 화면은 그 JSON을 받아 "등록 성공, 청구계정번호: 2000000123"을 표시한다.

왜 Entity를 그대로 안 주고 View로 바꾸나? Entity에는 등록자ID·변경일시 같은 내부 컬럼도 있다. View는 화면에 필요한 것만 담아, 불필요/민감 정보가 밖으로 새는 걸 막는다.

---

## 전체 흐름 한 번에

```
브라우저: "등록" 클릭
   │  POST /api/autopay/billing-accounts + JSON{tenantId, customerNo, ...}
   ▼
Controller.register(@RequestBody Command)      JSON → Command 자동 변환
   │  billingAccountService.register(command)
   ▼
Service.register(Command)
   │  1. new Entity()                DB 저장용 빈 상자
   │  2. 청구계정번호 채번 (2+9자리)
   │  3. Command 값 → Entity 복사 + useYn="Y"
   │  4. mapper.insert(entity)
   ▼
Mapper.insert(Entity)               BaseMapper가 INSERT SQL 자동 생성
   │  (MetaObjectHandler가 등록일시/등록자 P215138 자동 채움)
   ▼
MySQL: BILLING_ACCOUNT 에 INSERT
   │
   ▼ (역방향으로 결과 반환)
Service: Entity → View 변환
   ▼
Controller: View 반환 → 스프링이 JSON으로 변환
   ▼
브라우저: "등록 성공" 표시
```

---

## 왜 이렇게 여러 조각으로 나눌까? (핵심)

한 파일에 다 넣어도 동작은 한다. 그런데 Controller / Service / Command / Entity / View / Mapper로 나눈 이유:

- 각자 역할이 하나뿐이라 어디를 고칠지 명확하다.
  - 화면 주소가 바뀌면 → Controller
  - 업무 규칙(채번 등)이 바뀌면 → Service
  - 테이블 컬럼이 바뀌면 → Entity
- 화면/업무/DB가 서로 덜 얽힌다. 화면을 앱으로 바꾸거나 DB를 바꿔도 나머지는 그대로.
- 상자를 나누는 이유(Command/Entity/View): 들어오는 값·저장되는 값·나가는 값이 각각 다르기 때문. 하나로 쓰면 안 보여줄 값이 새거나, 화면이 안 보낸 값 때문에 꼬인다.

처음엔 파일이 많아 보이지만, 규모가 커질수록 이 구조가 훨씬 안전하고 수정하기 쉽다.

---

## 용어 미니 사전

| 용어 | 초보자용 설명 |
|------|---------------|
| 어노테이션(@...) | 클래스/필드에 붙이는 "표시". 스프링/라이브러리가 이 표시를 보고 특별하게 처리 |
| DTO | 값만 담아 옮기는 상자 (Command/View가 여기 해당) |
| Entity | DB 테이블 한 줄에 대응하는 상자 |
| 의존성 주입 | 생성자에 필요한 객체(서비스/매퍼)를 스프링이 자동으로 넣어주는 것 |
| 트랜잭션 | "전부 성공 아니면 전부 취소" 단위. `@Transactional` |
| CRUD | 생성(Create)/조회(Read)/수정(Update)/삭제(Delete) |
| 채번 | 번호를 규칙에 따라 새로 만들어 붙이는 것 |
