package com.skt.autopay.paymeansregistration.domain.model;

import java.time.LocalDateTime;

/**
 * 납부수단 도메인 모델 (Aggregate Root)
 *
 * <p>납부수단_구조설계 §1.1.3, §3 기준. auth-first 원칙에 따라
 * 실시간인증 성공 후에만 생성되며, 등록 경로에 따라 초기 상태가 결정된다.
 * <ul>
 *   <li>본인 / 대리인 대면 : 활성(10)</li>
 *   <li>대리인 비대면      : 등록중(01) → 소유주 동의 시 활성(10)</li>
 * </ul>
 */
public class PayMeans {

    private Long payMeansNo;
    private String tenantId;
    private String customerNo;
    private PayMeansType type;
    private PayMeansStatus status;
    private String payMeansNm;
    private String fincInstCd;
    private String issuncCardcoCd;
    /** 계좌/카드 대체ID (실제 계좌·카드번호는 별도 암호화 저장) */
    private Long bankacctCardAltrnateId;
    private String cardValidYymm;
    private String holderNm;
    private String changeReasonCd;
    private LocalDateTime firstRegistDtm;

    protected PayMeans() {
    }

    /**
     * 본인명의 등록 (경로2) - 인증 성공 후 즉시 활성(10).
     */
    public static PayMeans registerByOwner(String tenantId,
                                           String customerNo,
                                           PayMeansType type,
                                           String payMeansNm,
                                           String fincInstCd,
                                           Long bankacctCardAltrnateId,
                                           String holderNm) {
        PayMeans m = base(tenantId, customerNo, type, payMeansNm, fincInstCd, bankacctCardAltrnateId, holderNm);
        m.status = PayMeansStatus.ACTIVE;
        m.changeReasonCd = "01"; // 신규 등록
        return m;
    }

    /**
     * 대리인 등록.
     * <ul>
     *   <li>대면: 본인과 동일하게 활성(10)</li>
     *   <li>비대면: 등록중(01) - 소유주 SMS 동의 대기</li>
     * </ul>
     * 간편결제(05)는 대리납부 불가.
     */
    public static PayMeans registerByAgent(String tenantId,
                                           String customerNo,
                                           PayMeansType type,
                                           String payMeansNm,
                                           String fincInstCd,
                                           Long bankacctCardAltrnateId,
                                           String holderNm,
                                           AgentRegistrationType agentType) {
        if (!type.isAgentPayable()) {
            throw new IllegalArgumentException("간편결제는 대리납부(타인명의) 등록이 불가합니다.");
        }
        PayMeans m = base(tenantId, customerNo, type, payMeansNm, fincInstCd, bankacctCardAltrnateId, holderNm);
        if (agentType == AgentRegistrationType.FACE_TO_FACE) {
            m.status = PayMeansStatus.ACTIVE;
            m.changeReasonCd = "01";
        } else {
            m.status = PayMeansStatus.REGISTERING; // 01 등록중
            m.changeReasonCd = "01";
        }
        return m;
    }

    private static PayMeans base(String tenantId,
                                 String customerNo,
                                 PayMeansType type,
                                 String payMeansNm,
                                 String fincInstCd,
                                 Long bankacctCardAltrnateId,
                                 String holderNm) {
        PayMeans m = new PayMeans();
        m.tenantId = tenantId;
        m.customerNo = customerNo;
        m.type = type;
        m.payMeansNm = payMeansNm;
        m.fincInstCd = fincInstCd;
        m.bankacctCardAltrnateId = bankacctCardAltrnateId;
        m.holderNm = holderNm;
        m.firstRegistDtm = LocalDateTime.now();
        return m;
    }

    /**
     * 대리납부 비대면 2차 - 소유주 동의 완료. 등록중(01) → 활성(10).
     */
    public void approveByOwner() {
        requireStatus(PayMeansStatus.REGISTERING);
        this.status = PayMeansStatus.ACTIVE;
        this.changeReasonCd = "02"; // 인증대기/동의 후 완료
    }

    /**
     * 대리납부 비대면 2차 - 소유주 거절/지연. 등록중(01) → 종료(40).
     */
    public void closeByOwnerReject() {
        requireStatus(PayMeansStatus.REGISTERING);
        this.status = PayMeansStatus.CLOSED;
    }

    /** U4 별칭/연락처 정보변경. 상태 전이 없음. null 인자는 미변경. */
    public void changeInfo(String newPayMeansNm) {
        if (newPayMeansNm != null) {
            this.payMeansNm = newPayMeansNm;
        }
    }

    /** 사용중지 (10 → 20). 신규 매핑 차단, 기존 유지. */
    public void suspend() {
        requireStatus(PayMeansStatus.ACTIVE);
        this.status = PayMeansStatus.SUSPENDED;
    }

    /** 사용재개 (20 → 10). 정지 상태에서만 가능. */
    public void resume() {
        requireStatus(PayMeansStatus.SUSPENDED);
        this.status = PayMeansStatus.ACTIVE;
    }

    /** 해지 (10/20 → 30). */
    public void terminate() {
        if (status != PayMeansStatus.ACTIVE && status != PayMeansStatus.SUSPENDED) {
            throw new IllegalStateException("해지 불가 상태: " + status);
        }
        this.status = PayMeansStatus.TERMINATED;
    }

    private void requireStatus(PayMeansStatus expected) {
        if (this.status != expected) {
            throw new IllegalStateException(
                    "상태 전이 불가: 현재=" + this.status + ", 기대=" + expected);
        }
    }

    /**
     * 영속 데이터로부터 도메인 상태를 그대로 복원 (신규 생성 아님).
     */
    public static PayMeans reconstitute(Long payMeansNo, String tenantId, String customerNo,
                                        PayMeansType type, PayMeansStatus status, String payMeansNm,
                                        String fincInstCd, Long bankacctCardAltrnateId,
                                        String holderNm, String changeReasonCd) {
        PayMeans m = new PayMeans();
        m.payMeansNo = payMeansNo;
        m.tenantId = tenantId;
        m.customerNo = customerNo;
        m.type = type;
        m.status = status;
        m.payMeansNm = payMeansNm;
        m.fincInstCd = fincInstCd;
        m.bankacctCardAltrnateId = bankacctCardAltrnateId;
        m.holderNm = holderNm;
        m.changeReasonCd = changeReasonCd;
        return m;
    }

    public boolean isNonFacePending() {
        return this.status == PayMeansStatus.REGISTERING;
    }

    // getters / seqno setter
    public Long getPayMeansNo() {
        return payMeansNo;
    }

    public void setPayMeansNo(Long payMeansNo) {
        this.payMeansNo = payMeansNo;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public PayMeansType getType() {
        return type;
    }

    public PayMeansStatus getStatus() {
        return status;
    }

    public String getPayMeansNm() {
        return payMeansNm;
    }

    public String getFincInstCd() {
        return fincInstCd;
    }

    public String getIssuncCardcoCd() {
        return issuncCardcoCd;
    }

    public Long getBankacctCardAltrnateId() {
        return bankacctCardAltrnateId;
    }

    public String getCardValidYymm() {
        return cardValidYymm;
    }

    public String getHolderNm() {
        return holderNm;
    }

    public String getChangeReasonCd() {
        return changeReasonCd;
    }

    public LocalDateTime getFirstRegistDtm() {
        return firstRegistDtm;
    }
}
