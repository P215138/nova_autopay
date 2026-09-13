package com.skt.autopay.paymeansregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 대리납부 맵핑 (PAY_AGENT_MAP) 엔티티
 *
 * <p>납부수단_구조설계 §2.3 기준.
 * 타인명의 납부수단 사용 관계를 명시. 1수단 ↔ N매핑, soft delete(유효종료일시).
 */
@TableName("PAY_AGENT_MAP")
public class AgentPayMapEntity {

    /** 대리납부맵일련번호 (PK) */
    @TableId(value = "AGENT_PAY_MAP_SEQNO", type = IdType.INPUT)
    private Long agentPayMapSeqno;

    /** 테넌트ID */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 대리납부자 고객번호 (수단 소유주 - 돈 빠지는 사람) */
    @TableField("AGENT_PAYER_CUSTOMER_NO")
    private String agentPayerCustomerNo;

    /** 피대리납부자 고객번호 (수단 사용자 - 혜택 받는 사람) */
    @TableField("BNFC_PAYER_CUSTOMER_NO")
    private String bnfcPayerCustomerNo;

    /** 납부수단번호 (FK) */
    @TableField("PAY_MEANS_NO")
    private Long payMeansNo;

    /** 관계구분코드 (부모/배우자/자녀) */
    @TableField("REL_CATG_CD")
    private String relCatgCd;

    /** 상태코드 (10 요청/20 활성/30 거절/40 중단/50 종료) */
    @TableField("AGENT_PAY_MAP_STAT_CD")
    private String agentPayMapStatCd;

    /** 유효시작일시 */
    @TableField("VALID_START_DTM")
    private LocalDateTime validStartDtm;

    /** 유효종료일시 (soft delete) */
    @TableField("VALID_END_DTM")
    private LocalDateTime validEndDtm;

    /** 최초등록일시 */
    @TableField(value = "FIRST_REGIST_DTM", fill = FieldFill.INSERT)
    private LocalDateTime firstRegistDtm;

    /** 최초등록자ID */
    @TableField(value = "FIRST_REGISTR_ID", fill = FieldFill.INSERT)
    private String firstRegistrId;

    /** 최종변경일시 */
    @TableField(value = "FINAL_CHG_DTM", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime finalChgDtm;

    /** 최종변경자ID */
    @TableField(value = "FINAL_CHGR_ID", fill = FieldFill.INSERT_UPDATE)
    private String finalChgrId;

    public Long getAgentPayMapSeqno() {
        return agentPayMapSeqno;
    }

    public void setAgentPayMapSeqno(Long agentPayMapSeqno) {
        this.agentPayMapSeqno = agentPayMapSeqno;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAgentPayerCustomerNo() {
        return agentPayerCustomerNo;
    }

    public void setAgentPayerCustomerNo(String agentPayerCustomerNo) {
        this.agentPayerCustomerNo = agentPayerCustomerNo;
    }

    public String getBnfcPayerCustomerNo() {
        return bnfcPayerCustomerNo;
    }

    public void setBnfcPayerCustomerNo(String bnfcPayerCustomerNo) {
        this.bnfcPayerCustomerNo = bnfcPayerCustomerNo;
    }

    public Long getPayMeansNo() {
        return payMeansNo;
    }

    public void setPayMeansNo(Long payMeansNo) {
        this.payMeansNo = payMeansNo;
    }

    public String getRelCatgCd() {
        return relCatgCd;
    }

    public void setRelCatgCd(String relCatgCd) {
        this.relCatgCd = relCatgCd;
    }

    public String getAgentPayMapStatCd() {
        return agentPayMapStatCd;
    }

    public void setAgentPayMapStatCd(String agentPayMapStatCd) {
        this.agentPayMapStatCd = agentPayMapStatCd;
    }

    public LocalDateTime getValidStartDtm() {
        return validStartDtm;
    }

    public void setValidStartDtm(LocalDateTime validStartDtm) {
        this.validStartDtm = validStartDtm;
    }

    public LocalDateTime getValidEndDtm() {
        return validEndDtm;
    }

    public void setValidEndDtm(LocalDateTime validEndDtm) {
        this.validEndDtm = validEndDtm;
    }

    public LocalDateTime getFirstRegistDtm() {
        return firstRegistDtm;
    }

    public void setFirstRegistDtm(LocalDateTime firstRegistDtm) {
        this.firstRegistDtm = firstRegistDtm;
    }

    public String getFirstRegistrId() {
        return firstRegistrId;
    }

    public void setFirstRegistrId(String firstRegistrId) {
        this.firstRegistrId = firstRegistrId;
    }

    public LocalDateTime getFinalChgDtm() {
        return finalChgDtm;
    }

    public void setFinalChgDtm(LocalDateTime finalChgDtm) {
        this.finalChgDtm = finalChgDtm;
    }

    public String getFinalChgrId() {
        return finalChgrId;
    }

    public void setFinalChgrId(String finalChgrId) {
        this.finalChgrId = finalChgrId;
    }
}
