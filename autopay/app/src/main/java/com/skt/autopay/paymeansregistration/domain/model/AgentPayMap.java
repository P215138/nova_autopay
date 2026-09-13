package com.skt.autopay.paymeansregistration.domain.model;

import java.time.LocalDateTime;

/**
 * 대리납부 맵핑 도메인 모델 (Aggregate)
 *
 * <p>납부수단_구조설계 §2.3, §4.1 기준.
 * 타인명의 납부수단 사용 관계와 상태 전이(요청→활성/거절)를 담는다.
 */
public class AgentPayMap {

    private Long agentPayMapSeqno;
    private String tenantId;
    /** 대리납부자 고객번호 (수단 소유주) */
    private String agentPayerCustomerNo;
    /** 피대리납부자 고객번호 (수단 사용자) */
    private String bnfcPayerCustomerNo;
    private Long payMeansNo;
    private String relCatgCd;
    private AgentPayMapStatus status;
    private LocalDateTime validStartDtm;
    private LocalDateTime validEndDtm;

    protected AgentPayMap() {
    }

    /**
     * U9 대리납부 등록 요청 - 요청(REQ) 상태로 신규 생성.
     */
    public static AgentPayMap request(String tenantId,
                                      Long payMeansNo,
                                      String agentPayerCustomerNo,
                                      String bnfcPayerCustomerNo,
                                      String relCatgCd) {
        AgentPayMap m = new AgentPayMap();
        m.tenantId = tenantId;
        m.payMeansNo = payMeansNo;
        m.agentPayerCustomerNo = agentPayerCustomerNo;
        m.bnfcPayerCustomerNo = bnfcPayerCustomerNo;
        m.relCatgCd = relCatgCd;
        m.status = AgentPayMapStatus.REQ;
        m.validStartDtm = LocalDateTime.now();
        return m;
    }

    /**
     * U10 소유주 동의 완료 - 활성(ACT)으로 전이.
     */
    public void approve() {
        requireStatus(AgentPayMapStatus.REQ);
        this.status = AgentPayMapStatus.ACT;
    }

    /**
     * U10 소유주 거절 - 거절(REJ)로 전이 + soft delete.
     */
    public void reject() {
        requireStatus(AgentPayMapStatus.REQ);
        this.status = AgentPayMapStatus.REJ;
        this.validEndDtm = LocalDateTime.now();
    }

    /**
     * U12 대리납부 관계 해지 - 종료(TRM)로 전이 + soft delete.
     */
    public void terminate() {
        this.status = AgentPayMapStatus.TRM;
        this.validEndDtm = LocalDateTime.now();
    }

    private void requireStatus(AgentPayMapStatus expected) {
        if (this.status != expected) {
            throw new IllegalStateException(
                    "상태 전이 불가: 현재=" + this.status + ", 기대=" + expected);
        }
    }

    /**
     * 영속 데이터로부터 도메인 상태를 그대로 복원.
     */
    public static AgentPayMap reconstitute(Long agentPayMapSeqno, String tenantId,
                                           String agentPayerCustomerNo, String bnfcPayerCustomerNo,
                                           Long payMeansNo, String relCatgCd,
                                           AgentPayMapStatus status,
                                           LocalDateTime validStartDtm, LocalDateTime validEndDtm) {
        AgentPayMap m = new AgentPayMap();
        m.agentPayMapSeqno = agentPayMapSeqno;
        m.tenantId = tenantId;
        m.agentPayerCustomerNo = agentPayerCustomerNo;
        m.bnfcPayerCustomerNo = bnfcPayerCustomerNo;
        m.payMeansNo = payMeansNo;
        m.relCatgCd = relCatgCd;
        m.status = status;
        m.validStartDtm = validStartDtm;
        m.validEndDtm = validEndDtm;
        return m;
    }

    public Long getAgentPayMapSeqno() {
        return agentPayMapSeqno;
    }

    public void setAgentPayMapSeqno(Long agentPayMapSeqno) {
        this.agentPayMapSeqno = agentPayMapSeqno;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getAgentPayerCustomerNo() {
        return agentPayerCustomerNo;
    }

    public String getBnfcPayerCustomerNo() {
        return bnfcPayerCustomerNo;
    }

    public Long getPayMeansNo() {
        return payMeansNo;
    }

    public String getRelCatgCd() {
        return relCatgCd;
    }

    public AgentPayMapStatus getStatus() {
        return status;
    }

    public LocalDateTime getValidStartDtm() {
        return validStartDtm;
    }

    public LocalDateTime getValidEndDtm() {
        return validEndDtm;
    }
}
