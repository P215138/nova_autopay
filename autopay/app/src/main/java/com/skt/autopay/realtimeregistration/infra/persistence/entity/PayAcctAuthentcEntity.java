package com.skt.autopay.realtimeregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 납부계정인증 (PAY_ACCT_AUTHENTC) 엔티티
 *
 * <p>entity-definitions.md 기준. 한글 논리명을 영문 물리명으로 변환하여 생성.
 * PK 복합키: 납부계정번호+납부수단순번+테넌트ID+인증순번.
 */
@TableName("PAY_ACCT_AUTHENTC")
public class PayAcctAuthentcEntity {

    /** 납부계정번호 (PK) */
    @TableField("PAY_ACCT_NO")
    private Long payAcctNo;

    /** 납부수단순번 (PK) */
    @TableField("PAY_MEANS_SEQ")
    private Long payMeansSeq;

    /** 테넌트ID (PK) */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 인증순번 (PK) */
    @TableField("AUTHENTC_SEQ")
    private Long authentcSeq;

    /** 납부방법코드 */
    @TableField("PAY_MTHD_CD")
    private String payMthdCd;

    /** 인증방법코드 */
    @TableField("AUTHENTC_MTHD_CD")
    private String authentcMthdCd;

    /** 인증상태코드 */
    @TableField("AUTHENTC_STAT_CD")
    private String authentcStatCd;

    /** 인증요청일시 */
    @TableField("AUTHENTC_REQ_DTM")
    private LocalDateTime authentcReqDtm;

    /** 인증완료일시 */
    @TableField("AUTHENTC_CMPL_DTM")
    private LocalDateTime authentcCmplDtm;

    /** 인증만료일시 */
    @TableField("AUTHENTC_EXP_DTM")
    private LocalDateTime authentcExpDtm;

    /** 인증채널코드 */
    @TableField("AUTHENTC_CHNL_CD")
    private String authentcChnlCd;

    /** 인증기관코드 */
    @TableField("AUTHENTC_INST_CD")
    private String authentcInstCd;

    /** 인증결과코드 */
    @TableField("AUTHENTC_RESULT_CD")
    private String authentcResultCd;

    /** 인증결과메시지 */
    @TableField("AUTHENTC_RESULT_MSG")
    private String authentcResultMsg;

    /** 외부인증키 */
    @TableField("EXTNL_AUTHENTC_KEY")
    private String extnlAuthentcKey;

    /** 외부인증결과코드 */
    @TableField("EXTNL_AUTHENTC_RESULT_CD")
    private String extnlAuthentcResultCd;

    /** 외부인증결과메시지 */
    @TableField("EXTNL_AUTHENTC_RESULT_MSG")
    private String extnlAuthentcResultMsg;

    /** 카드승인번호 */
    @TableField("CARD_APPRV_NO")
    private String cardApprvNo;

    /** 카드승인일시 */
    @TableField("CARD_APPRV_DTM")
    private LocalDateTime cardApprvDtm;

    /** 카드승인금액 */
    @TableField("CARD_APPRV_AMT")
    private BigDecimal cardApprvAmt;

    /** 카드할부개월 */
    @TableField("CARD_INSTLMT_MONTH")
    private Integer cardInstlmtMonth;

    /** 카드무이자여부 */
    @TableField("CARD_NO_INTRST_YN")
    private String cardNoIntrstYn;

    /** CMS인증번호 */
    @TableField("CMS_AUTHENTC_NO")
    private String cmsAuthentcNo;

    /** CMS인증일시 */
    @TableField("CMS_AUTHENTC_DTM")
    private LocalDateTime cmsAuthentcDtm;

    /** EDI인증번호 */
    @TableField("EDI_AUTHENTC_NO")
    private String ediAuthentcNo;

    /** EDI인증일시 */
    @TableField("EDI_AUTHENTC_DTM")
    private LocalDateTime ediAuthentcDtm;

    /** EDI인증결과코드 */
    @TableField("EDI_AUTHENTC_RESULT_CD")
    private String ediAuthentcResultCd;

    /** EDI인증결과메시지 */
    @TableField("EDI_AUTHENTC_RESULT_MSG")
    private String ediAuthentcResultMsg;

    /** FB인증번호 */
    @TableField("FB_AUTHENTC_NO")
    private String fbAuthentcNo;

    /** FB인증일시 */
    @TableField("FB_AUTHENTC_DTM")
    private LocalDateTime fbAuthentcDtm;

    /** FB인증결과코드 */
    @TableField("FB_AUTHENTC_RESULT_CD")
    private String fbAuthentcResultCd;

    /** FB인증결과메시지 */
    @TableField("FB_AUTHENTC_RESULT_MSG")
    private String fbAuthentcResultMsg;

    /** 간편결제인증번호 */
    @TableField("SIMPLE_PAY_AUTHENTC_NO")
    private String simplePayAuthentcNo;

    /** 간편결제인증일시 */
    @TableField("SIMPLE_PAY_AUTHENTC_DTM")
    private LocalDateTime simplePayAuthentcDtm;

    /** 간편결제인증결과코드 */
    @TableField("SIMPLE_PAY_AUTHENTC_RESULT_CD")
    private String simplePayAuthentcResultCd;

    /** 간편결제인증결과메시지 */
    @TableField("SIMPLE_PAY_AUTHENTC_RESULT_MSG")
    private String simplePayAuthentcResultMsg;

    /** CMS이용점코드 */
    @TableField("CMS_USE_STORE_CD")
    private String cmsUseStoreCd;

    /** CMS이용점명 */
    @TableField("CMS_USE_STORE_NM")
    private String cmsUseStoreNm;

    /** 대리인납부여부 */
    @TableField("AGENT_PAY_YN")
    private String agentPayYn;

    /** 대리인고객번호 */
    @TableField("AGENT_CUSTOMER_NO")
    private String agentCustomerNo;

    /** 대리인인증번호 */
    @TableField("AGENT_AUTHENTC_NO")
    private String agentAuthentcNo;

    /** 대리인인증일시 */
    @TableField("AGENT_AUTHENTC_DTM")
    private LocalDateTime agentAuthentcDtm;

    /** 처리채널코드 */
    @TableField("PRCSSG_CHNL_CD")
    private String prcssgChnlCd;

    /** 처리자번호 */
    @TableField("PRCSSR_NO")
    private String prcssrNo;

    /** 처리일자 */
    @TableField("PRCSSG_DT")
    private String prcssgDt;

    /** 이전인증순번 */
    @TableField("PREV_AUTHENTC_SEQ")
    private Long prevAuthentcSeq;

    /** 재인증여부 */
    @TableField("RE_AUTHENTC_YN")
    private String reAuthentcYn;

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

    public Long getAuthentcSeq() {
        return authentcSeq;
    }

    public void setAuthentcSeq(Long authentcSeq) {
        this.authentcSeq = authentcSeq;
    }

    public String getPayMthdCd() {
        return payMthdCd;
    }

    public void setPayMthdCd(String payMthdCd) {
        this.payMthdCd = payMthdCd;
    }

    public String getAuthentcMthdCd() {
        return authentcMthdCd;
    }

    public void setAuthentcMthdCd(String authentcMthdCd) {
        this.authentcMthdCd = authentcMthdCd;
    }

    public String getAuthentcStatCd() {
        return authentcStatCd;
    }

    public void setAuthentcStatCd(String authentcStatCd) {
        this.authentcStatCd = authentcStatCd;
    }

    public LocalDateTime getAuthentcReqDtm() {
        return authentcReqDtm;
    }

    public void setAuthentcReqDtm(LocalDateTime authentcReqDtm) {
        this.authentcReqDtm = authentcReqDtm;
    }

    public LocalDateTime getAuthentcCmplDtm() {
        return authentcCmplDtm;
    }

    public void setAuthentcCmplDtm(LocalDateTime authentcCmplDtm) {
        this.authentcCmplDtm = authentcCmplDtm;
    }

    public LocalDateTime getAuthentcExpDtm() {
        return authentcExpDtm;
    }

    public void setAuthentcExpDtm(LocalDateTime authentcExpDtm) {
        this.authentcExpDtm = authentcExpDtm;
    }

    public String getAuthentcChnlCd() {
        return authentcChnlCd;
    }

    public void setAuthentcChnlCd(String authentcChnlCd) {
        this.authentcChnlCd = authentcChnlCd;
    }

    public String getAuthentcInstCd() {
        return authentcInstCd;
    }

    public void setAuthentcInstCd(String authentcInstCd) {
        this.authentcInstCd = authentcInstCd;
    }

    public String getAuthentcResultCd() {
        return authentcResultCd;
    }

    public void setAuthentcResultCd(String authentcResultCd) {
        this.authentcResultCd = authentcResultCd;
    }

    public String getAuthentcResultMsg() {
        return authentcResultMsg;
    }

    public void setAuthentcResultMsg(String authentcResultMsg) {
        this.authentcResultMsg = authentcResultMsg;
    }

    public String getExtnlAuthentcKey() {
        return extnlAuthentcKey;
    }

    public void setExtnlAuthentcKey(String extnlAuthentcKey) {
        this.extnlAuthentcKey = extnlAuthentcKey;
    }

    public String getExtnlAuthentcResultCd() {
        return extnlAuthentcResultCd;
    }

    public void setExtnlAuthentcResultCd(String extnlAuthentcResultCd) {
        this.extnlAuthentcResultCd = extnlAuthentcResultCd;
    }

    public String getExtnlAuthentcResultMsg() {
        return extnlAuthentcResultMsg;
    }

    public void setExtnlAuthentcResultMsg(String extnlAuthentcResultMsg) {
        this.extnlAuthentcResultMsg = extnlAuthentcResultMsg;
    }

    public String getCardApprvNo() {
        return cardApprvNo;
    }

    public void setCardApprvNo(String cardApprvNo) {
        this.cardApprvNo = cardApprvNo;
    }

    public LocalDateTime getCardApprvDtm() {
        return cardApprvDtm;
    }

    public void setCardApprvDtm(LocalDateTime cardApprvDtm) {
        this.cardApprvDtm = cardApprvDtm;
    }

    public BigDecimal getCardApprvAmt() {
        return cardApprvAmt;
    }

    public void setCardApprvAmt(BigDecimal cardApprvAmt) {
        this.cardApprvAmt = cardApprvAmt;
    }

    public Integer getCardInstlmtMonth() {
        return cardInstlmtMonth;
    }

    public void setCardInstlmtMonth(Integer cardInstlmtMonth) {
        this.cardInstlmtMonth = cardInstlmtMonth;
    }

    public String getCardNoIntrstYn() {
        return cardNoIntrstYn;
    }

    public void setCardNoIntrstYn(String cardNoIntrstYn) {
        this.cardNoIntrstYn = cardNoIntrstYn;
    }

    public String getCmsAuthentcNo() {
        return cmsAuthentcNo;
    }

    public void setCmsAuthentcNo(String cmsAuthentcNo) {
        this.cmsAuthentcNo = cmsAuthentcNo;
    }

    public LocalDateTime getCmsAuthentcDtm() {
        return cmsAuthentcDtm;
    }

    public void setCmsAuthentcDtm(LocalDateTime cmsAuthentcDtm) {
        this.cmsAuthentcDtm = cmsAuthentcDtm;
    }

    public String getEdiAuthentcNo() {
        return ediAuthentcNo;
    }

    public void setEdiAuthentcNo(String ediAuthentcNo) {
        this.ediAuthentcNo = ediAuthentcNo;
    }

    public LocalDateTime getEdiAuthentcDtm() {
        return ediAuthentcDtm;
    }

    public void setEdiAuthentcDtm(LocalDateTime ediAuthentcDtm) {
        this.ediAuthentcDtm = ediAuthentcDtm;
    }

    public String getEdiAuthentcResultCd() {
        return ediAuthentcResultCd;
    }

    public void setEdiAuthentcResultCd(String ediAuthentcResultCd) {
        this.ediAuthentcResultCd = ediAuthentcResultCd;
    }

    public String getEdiAuthentcResultMsg() {
        return ediAuthentcResultMsg;
    }

    public void setEdiAuthentcResultMsg(String ediAuthentcResultMsg) {
        this.ediAuthentcResultMsg = ediAuthentcResultMsg;
    }

    public String getFbAuthentcNo() {
        return fbAuthentcNo;
    }

    public void setFbAuthentcNo(String fbAuthentcNo) {
        this.fbAuthentcNo = fbAuthentcNo;
    }

    public LocalDateTime getFbAuthentcDtm() {
        return fbAuthentcDtm;
    }

    public void setFbAuthentcDtm(LocalDateTime fbAuthentcDtm) {
        this.fbAuthentcDtm = fbAuthentcDtm;
    }

    public String getFbAuthentcResultCd() {
        return fbAuthentcResultCd;
    }

    public void setFbAuthentcResultCd(String fbAuthentcResultCd) {
        this.fbAuthentcResultCd = fbAuthentcResultCd;
    }

    public String getFbAuthentcResultMsg() {
        return fbAuthentcResultMsg;
    }

    public void setFbAuthentcResultMsg(String fbAuthentcResultMsg) {
        this.fbAuthentcResultMsg = fbAuthentcResultMsg;
    }

    public String getSimplePayAuthentcNo() {
        return simplePayAuthentcNo;
    }

    public void setSimplePayAuthentcNo(String simplePayAuthentcNo) {
        this.simplePayAuthentcNo = simplePayAuthentcNo;
    }

    public LocalDateTime getSimplePayAuthentcDtm() {
        return simplePayAuthentcDtm;
    }

    public void setSimplePayAuthentcDtm(LocalDateTime simplePayAuthentcDtm) {
        this.simplePayAuthentcDtm = simplePayAuthentcDtm;
    }

    public String getSimplePayAuthentcResultCd() {
        return simplePayAuthentcResultCd;
    }

    public void setSimplePayAuthentcResultCd(String simplePayAuthentcResultCd) {
        this.simplePayAuthentcResultCd = simplePayAuthentcResultCd;
    }

    public String getSimplePayAuthentcResultMsg() {
        return simplePayAuthentcResultMsg;
    }

    public void setSimplePayAuthentcResultMsg(String simplePayAuthentcResultMsg) {
        this.simplePayAuthentcResultMsg = simplePayAuthentcResultMsg;
    }

    public String getCmsUseStoreCd() {
        return cmsUseStoreCd;
    }

    public void setCmsUseStoreCd(String cmsUseStoreCd) {
        this.cmsUseStoreCd = cmsUseStoreCd;
    }

    public String getCmsUseStoreNm() {
        return cmsUseStoreNm;
    }

    public void setCmsUseStoreNm(String cmsUseStoreNm) {
        this.cmsUseStoreNm = cmsUseStoreNm;
    }

    public String getAgentPayYn() {
        return agentPayYn;
    }

    public void setAgentPayYn(String agentPayYn) {
        this.agentPayYn = agentPayYn;
    }

    public String getAgentCustomerNo() {
        return agentCustomerNo;
    }

    public void setAgentCustomerNo(String agentCustomerNo) {
        this.agentCustomerNo = agentCustomerNo;
    }

    public String getAgentAuthentcNo() {
        return agentAuthentcNo;
    }

    public void setAgentAuthentcNo(String agentAuthentcNo) {
        this.agentAuthentcNo = agentAuthentcNo;
    }

    public LocalDateTime getAgentAuthentcDtm() {
        return agentAuthentcDtm;
    }

    public void setAgentAuthentcDtm(LocalDateTime agentAuthentcDtm) {
        this.agentAuthentcDtm = agentAuthentcDtm;
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

    public Long getPrevAuthentcSeq() {
        return prevAuthentcSeq;
    }

    public void setPrevAuthentcSeq(Long prevAuthentcSeq) {
        this.prevAuthentcSeq = prevAuthentcSeq;
    }

    public String getReAuthentcYn() {
        return reAuthentcYn;
    }

    public void setReAuthentcYn(String reAuthentcYn) {
        this.reAuthentcYn = reAuthentcYn;
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
