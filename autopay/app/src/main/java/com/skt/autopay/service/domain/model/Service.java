package com.skt.autopay.service.domain.model;

/**
 * 서비스(이동전화) 도메인 모델 (Aggregate Root)
 *
 * <p>청구계정과 BILLING_ACCOUNT_NO 로 연동. 상태 전이 규칙을 도메인이 소유한다.
 * <ul>
 *   <li>등록: 사용중(AC)</li>
 *   <li>정지: AC -> SP</li>
 *   <li>재개: SP -> AC</li>
 *   <li>해지: AC/SP -> TG</li>
 * </ul>
 * 서비스관리번호 채번은 인프라(어댑터) 책임이며 도메인은 관여하지 않는다.
 */
public class Service {

    private String serviceMgmtNo;
    private String tenantId;
    private String billingAccountNo;
    private String serviceNoAltrnateId;
    private ServiceStatus status;

    protected Service() {
    }

    /**
     * 신규 등록 - 상태 사용중(AC). 서비스관리번호는 저장 시 어댑터가 채번하여 채운다.
     */
    public static Service register(String tenantId,
                                   String billingAccountNo,
                                   String serviceNoAltrnateId) {
        Service s = new Service();
        s.tenantId = tenantId;
        s.billingAccountNo = billingAccountNo;
        s.serviceNoAltrnateId = serviceNoAltrnateId;
        s.status = ServiceStatus.ACTIVE;
        return s;
    }

    /** 정지 (AC -> SP) */
    public void suspend() {
        requireStatus(ServiceStatus.ACTIVE, "사용중 상태만 정지할 수 있습니다.");
        this.status = ServiceStatus.SUSPENDED;
    }

    /** 재개 (SP -> AC) */
    public void resume() {
        requireStatus(ServiceStatus.SUSPENDED, "정지 상태만 재개할 수 있습니다.");
        this.status = ServiceStatus.ACTIVE;
    }

    /** 해지 (AC/SP -> TG) */
    public void terminate() {
        if (status != ServiceStatus.ACTIVE && status != ServiceStatus.SUSPENDED) {
            throw new IllegalStateException("해지 불가 상태입니다: " + statusLabel());
        }
        this.status = ServiceStatus.TERMINATED;
    }

    private void requireStatus(ServiceStatus expected, String errMsg) {
        if (this.status != expected) {
            throw new IllegalStateException(errMsg + " (현재: " + statusLabel() + ")");
        }
    }

    private String statusLabel() {
        return status != null ? status.label() : "-";
    }

    /**
     * 영속 데이터로부터 도메인 상태를 그대로 복원 (신규 생성 아님).
     */
    public static Service reconstitute(String serviceMgmtNo, String tenantId,
                                       String billingAccountNo, String serviceNoAltrnateId,
                                       ServiceStatus status) {
        Service s = new Service();
        s.serviceMgmtNo = serviceMgmtNo;
        s.tenantId = tenantId;
        s.billingAccountNo = billingAccountNo;
        s.serviceNoAltrnateId = serviceNoAltrnateId;
        s.status = status;
        return s;
    }

    // 채번된 번호 반영 (저장 시 어댑터가 사용)
    public void assignServiceMgmtNo(String serviceMgmtNo) {
        this.serviceMgmtNo = serviceMgmtNo;
    }

    public String getServiceMgmtNo() {
        return serviceMgmtNo;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getBillingAccountNo() {
        return billingAccountNo;
    }

    public String getServiceNoAltrnateId() {
        return serviceNoAltrnateId;
    }

    public ServiceStatus getStatus() {
        return status;
    }
}
