package com.skt.autopay.realtimeregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 납부계정접수 (PAY_ACCT_RCPTN) 엔티티
 *
 * <p>entity-definitions.md 기준. 한글 논리명을 영문 물리명으로 변환하여 생성했으므로
 * 실제 테이블 정의서와 컬럼명이 다를 수 있음. PK는 복합키(납부계정번호+납부수단순번+테넌트ID+자동납부등록순번)로,
 * MyBatis-Plus 단일 @TableId 제약상 별도 매퍼에서 복합키를 다루거나 @TableId는 대표 컬럼에만 부여.
 */
@TableName("PAY_ACCT_RCPTN")
public class PayAcctRcptnEntity {

    /** 납부계정번호 (PK) */
    @TableField("PAY_ACCT_NO")
    private Long payAcctNo;

    /** 납부수단순번 (PK) */
    @TableField("PAY_MEANS_SEQ")
    private Long payMeansSeq;

    /** 테넌트ID (PK) */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 자동납부등록순번 (PK) */
    @TableField("AUTO_PAY_REGIST_SEQ")
    private Long autoPayRegistSeq;

    /** 납부방법코드 */
    @TableField("PAY_MTHD_CD")
    private String payMthdCd;

    /** 납부계정상태코드 */
    @TableField("PAY_ACCT_STAT_CD")
    private String payAcctStatCd;

    /** 납부금액유형코드 */
    @TableField("PAY_AMT_TYPE_CD")
    private String payAmtTypeCd;

    /** 납부주기코드 */
    @TableField("PAY_CYCLE_CD")
    private String payCycleCd;

    /** 납부주기일코드 */
    @TableField("PAY_CYCLE_DAY_CD")
    private String payCycleDayCd;

    /** 자동납부등록일자 */
    @TableField("AUTO_PAY_REGIST_DT")
    private String autoPayRegistDt;

    /** 자동납부해지일자 */
    @TableField("AUTO_PAY_TERMINATE_DT")
    private String autoPayTerminateDt;

    /** 자동납부등록채널코드 */
    @TableField("AUTO_PAY_REGIST_CHNL_CD")
    private String autoPayRegistChnlCd;

    /** 자동납부해지채널코드 */
    @TableField("AUTO_PAY_TERMINATE_CHNL_CD")
    private String autoPayTerminateChnlCd;

    /** 납부수단변경일자 */
    @TableField("PAY_MEANS_CHG_DT")
    private String payMeansChgDt;

    /** 변경채널코드 */
    @TableField("CHG_CHNL_CD")
    private String chgChnlCd;

    /** 납부기관코드 */
    @TableField("PAY_INST_CD")
    private String payInstCd;

    /** 납부기관계좌코드 */
    @TableField("PAY_INST_ACCT_CD")
    private String payInstAcctCd;

    /** 계좌카드대체ID */
    @TableField("BANKACCT_CARD_ALTRNATE_ID")
    private Long bankacctCardAltrnateId;

    /** 인증방법코드 */
    @TableField("AUTHENTC_MTHD_CD")
    private String authentcMthdCd;

    /** EDI서비스코드 */
    @TableField("EDI_SVC_CD")
    private String ediSvcCd;

    /** FB서비스코드 */
    @TableField("FB_SVC_CD")
    private String fbSvcCd;

    /** 인출유형상세코드 */
    @TableField("WDRW_TYPE_DTL_CD")
    private String wdrwTypeDtlCd;

    /** 납부대리인여부 */
    @TableField("PAY_AGENT_YN")
    private String payAgentYn;

    /** 대리납부계정번호 */
    @TableField("AGENT_PAY_ACCT_NO")
    private Long agentPayAcctNo;

    /** 납부금액 */
    @TableField("PAY_AMT")
    private BigDecimal payAmt;

    /** CMS코드 */
    @TableField("CMS_CD")
    private String cmsCd;

    /** 신청기관코드 */
    @TableField("APPLREQ_INST_CD")
    private String applreqInstCd;

    /** 처리채널코드 */
    @TableField("PRCSSG_CHNL_CD")
    private String prcssgChnlCd;

    /** 처리자번호 */
    @TableField("PRCSSR_NO")
    private String prcssrNo;

    /** 처리일자 */
    @TableField("PRCSSG_DT")
    private String prcssgDt;

    /** 처리결과코드 */
    @TableField("PRCSSG_RESULT_CD")
    private String prcssgResultCd;

    /** 처리결과메시지 */
    @TableField("PRCSSG_RESULT_MSG")
    private String prcssgResultMsg;

    /** 이전납부방법코드 */
    @TableField("PREV_PAY_MTHD_CD")
    private String prevPayMthdCd;

    /** 이전납부수단ID */
    @TableField("PREV_PAY_MEANS_ID")
    private Long prevPayMeansId;

    /** 이전납부기관코드 */
    @TableField("PREV_PAY_INST_CD")
    private String prevPayInstCd;

    /** 이전납부기관계좌코드 */
    @TableField("PREV_PAY_INST_ACCT_CD")
    private String prevPayInstAcctCd;

    /** 요청사유 */
    @TableField("REQ_REASON")
    private String reqReason;

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

    public Long getPayAcctNo() {
        return payAcctNo;
    }

    public void setPayAcctNo(Long payAcctNo) {
        this.payAcctNo = payAcctNo;
    }

    public Long getPayMeansSeq() {
        return payMeansSeq;
    }

    public void setPayMeansSeq(Long payMeansSeq) {
        this.payMeansSeq = payMeansSeq;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getAutoPayRegistSeq() {
        return autoPayRegistSeq;
    }

    public void setAutoPayRegistSeq(Long autoPayRegistSeq) {
        this.autoPayRegistSeq = autoPayRegistSeq;
    }

    public String getPayMthdCd() {
        return payMthdCd;
    }

    public void setPayMthdCd(String payMthdCd) {
        this.payMthdCd = payMthdCd;
    }

    public String getPayAcctStatCd() {
        return payAcctStatCd;
    }

    public void setPayAcctStatCd(String payAcctStatCd) {
        this.payAcctStatCd = payAcctStatCd;
    }

    public String getPayAmtTypeCd() {
        return payAmtTypeCd;
    }

    public void setPayAmtTypeCd(String payAmtTypeCd) {
        this.payAmtTypeCd = payAmtTypeCd;
    }

    public String getPayCycleCd() {
        return payCycleCd;
    }

    public void setPayCycleCd(String payCycleCd) {
        this.payCycleCd = payCycleCd;
    }

    public String getPayCycleDayCd() {
        return payCycleDayCd;
    }

    public void setPayCycleDayCd(String payCycleDayCd) {
        this.payCycleDayCd = payCycleDayCd;
    }

    public String getAutoPayRegistDt() {
        return autoPayRegistDt;
    }

    public void setAutoPayRegistDt(String autoPayRegistDt) {
        this.autoPayRegistDt = autoPayRegistDt;
    }

    public String getAutoPayTerminateDt() {
        return autoPayTerminateDt;
    }

    public void setAutoPayTerminateDt(String autoPayTerminateDt) {
        this.autoPayTerminateDt = autoPayTerminateDt;
    }

    public String getAutoPayRegistChnlCd() {
        return autoPayRegistChnlCd;
    }

    public void setAutoPayRegistChnlCd(String autoPayRegistChnlCd) {
        this.autoPayRegistChnlCd = autoPayRegistChnlCd;
    }

    public String getAutoPayTerminateChnlCd() {
        return autoPayTerminateChnlCd;
    }

    public void setAutoPayTerminateChnlCd(String autoPayTerminateChnlCd) {
        this.autoPayTerminateChnlCd = autoPayTerminateChnlCd;
    }

    public String getPayMeansChgDt() {
        return payMeansChgDt;
    }

    public void setPayMeansChgDt(String payMeansChgDt) {
        this.payMeansChgDt = payMeansChgDt;
    }

    public String getChgChnlCd() {
        return chgChnlCd;
    }

    public void setChgChnlCd(String chgChnlCd) {
        this.chgChnlCd = chgChnlCd;
    }

    public String getPayInstCd() {
        return payInstCd;
    }

    public void setPayInstCd(String payInstCd) {
        this.payInstCd = payInstCd;
    }

    public String getPayInstAcctCd() {
        return payInstAcctCd;
    }

    public void setPayInstAcctCd(String payInstAcctCd) {
        this.payInstAcctCd = payInstAcctCd;
    }

    public Long getBankacctCardAltrnateId() {
        return bankacctCardAltrnateId;
    }

    public void setBankacctCardAltrnateId(Long bankacctCardAltrnateId) {
        this.bankacctCardAltrnateId = bankacctCardAltrnateId;
    }

    public String getAuthentcMthdCd() {
        return authentcMthdCd;
    }

    public void setAuthentcMthdCd(String authentcMthdCd) {
        this.authentcMthdCd = authentcMthdCd;
    }

    public String getEdiSvcCd() {
        return ediSvcCd;
    }

    public void setEdiSvcCd(String ediSvcCd) {
        this.ediSvcCd = ediSvcCd;
    }

    public String getFbSvcCd() {
        return fbSvcCd;
    }

    public void setFbSvcCd(String fbSvcCd) {
        this.fbSvcCd = fbSvcCd;
    }

    public String getWdrwTypeDtlCd() {
        return wdrwTypeDtlCd;
    }

    public void setWdrwTypeDtlCd(String wdrwTypeDtlCd) {
        this.wdrwTypeDtlCd = wdrwTypeDtlCd;
    }

    public String getPayAgentYn() {
        return payAgentYn;
    }

    public void setPayAgentYn(String payAgentYn) {
        this.payAgentYn = payAgentYn;
    }

    public Long getAgentPayAcctNo() {
        return agentPayAcctNo;
    }

    public void setAgentPayAcctNo(Long agentPayAcctNo) {
        this.agentPayAcctNo = agentPayAcctNo;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public String getCmsCd() {
        return cmsCd;
    }

    public void setCmsCd(String cmsCd) {
        this.cmsCd = cmsCd;
    }

    public String getApplreqInstCd() {
        return applreqInstCd;
    }

    public void setApplreqInstCd(String applreqInstCd) {
        this.applreqInstCd = applreqInstCd;
    }

    public String getPrcssgChnlCd() {
        return prcssgChnlCd;
    }

    public void setPrcssgChnlCd(String prcssgChnlCd) {
        this.prcssgChnlCd = prcssgChnlCd;
    }

    public String getPrcssrNo() {
        return prcssrNo;
    }

    public void setPrcssrNo(String prcssrNo) {
        this.prcssrNo = prcssrNo;
    }

    public String getPrcssgDt() {
        return prcssgDt;
    }

    public void setPrcssgDt(String prcssgDt) {
        this.prcssgDt = prcssgDt;
    }

    public String getPrcssgResultCd() {
        return prcssgResultCd;
    }

    public void setPrcssgResultCd(String prcssgResultCd) {
        this.prcssgResultCd = prcssgResultCd;
    }

    public String getPrcssgResultMsg() {
        return prcssgResultMsg;
    }

    public void setPrcssgResultMsg(String prcssgResultMsg) {
        this.prcssgResultMsg = prcssgResultMsg;
    }

    public String getPrevPayMthdCd() {
        return prevPayMthdCd;
    }

    public void setPrevPayMthdCd(String prevPayMthdCd) {
        this.prevPayMthdCd = prevPayMthdCd;
    }

    public Long getPrevPayMeansId() {
        return prevPayMeansId;
    }

    public void setPrevPayMeansId(Long prevPayMeansId) {
        this.prevPayMeansId = prevPayMeansId;
    }

    public String getPrevPayInstCd() {
        return prevPayInstCd;
    }

    public void setPrevPayInstCd(String prevPayInstCd) {
        this.prevPayInstCd = prevPayInstCd;
    }

    public String getPrevPayInstAcctCd() {
        return prevPayInstAcctCd;
    }

    public void setPrevPayInstAcctCd(String prevPayInstAcctCd) {
        this.prevPayInstAcctCd = prevPayInstAcctCd;
    }

    public String getReqReason() {
        return reqReason;
    }

    public void setReqReason(String reqReason) {
        this.reqReason = reqReason;
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
