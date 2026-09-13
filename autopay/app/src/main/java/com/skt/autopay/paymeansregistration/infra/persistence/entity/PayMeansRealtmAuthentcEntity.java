package com.skt.autopay.paymeansregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 납부수단실시간인증 (PAY_MEANS_REALTM_AUTHENTC) 엔티티
 *
 * <p>PK: 실시간인증순번 + 테넌트ID. PAY_MEANS_ID는 PK에서 제외하고 맨 뒤에 두며,
 * auth-first 흐름상 인증 성공 후 채운다.
 * 설계상 소유는 extauth이나, 물리 테이블은 paymeans 도메인 하위로 배치.
 */
@TableName("PAY_MEANS_REALTM_AUTHENTC")
public class PayMeansRealtmAuthentcEntity {

    /** 실시간인증순번 (PK) */
    @TableId(value = "REALTM_AUTHENTC_SEQ", type = IdType.INPUT)
    private Long realtmAuthentcSeq;

    /** 테넌트ID (PK) */
    @TableField("TENANT_ID")
    private String tenantId;

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

    /** AID */
    @TableField("AID")
    private String aid;

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

    /** 납부수단상태변경코드 */
    @TableField("PAY_MEANS_STAT_CHG_CD")
    private String payMeansStatChgCd;

    /** 납부기관코드 */
    @TableField("PAY_INST_CD")
    private String payInstCd;

    /** 납부기관계좌코드 */
    @TableField("PAY_INST_ACCT_CD")
    private String payInstAcctCd;

    /** 은행계좌코드 */
    @TableField("BANK_ACCT_CD")
    private String bankAcctCd;

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

    /** CMS인증번호 */
    @TableField("CMS_AUTHENTC_NO")
    private String cmsAuthentcNo;

    /** CMS인증일시 */
    @TableField("CMS_AUTHENTC_DTM")
    private LocalDateTime cmsAuthentcDtm;

    /** CMS인증결과코드 */
    @TableField("CMS_AUTHENTC_RESULT_CD")
    private String cmsAuthentcResultCd;

    /** CMS이용점코드 */
    @TableField("CMS_USE_STORE_CD")
    private String cmsUseStoreCd;

    /** CMS이용점명 */
    @TableField("CMS_USE_STORE_NM")
    private String cmsUseStoreNm;

    /** 간편결제인증번호 */
    @TableField("SIMPLE_PAY_AUTHENTC_NO")
    private String simplePayAuthentcNo;

    /** 간편결제인증일시 */
    @TableField("SIMPLE_PAY_AUTHENTC_DTM")
    private LocalDateTime simplePayAuthentcDtm;

    /** 간편결제인증결과코드 */
    @TableField("SIMPLE_PAY_AUTHENTC_RESULT_CD")
    private String simplePayAuthentcResultCd;

    /** EDI납부수단변경코드 */
    @TableField("EDI_PAY_MEANS_CHG_CD")
    private String ediPayMeansChgCd;

    /** EDI납부수단변경결과코드 */
    @TableField("EDI_PAY_MEANS_CHG_RESULT_CD")
    private String ediPayMeansChgResultCd;

    /** 카드번호대체ID */
    @TableField("CARD_NO_ALTRNATE_ID")
    private Long cardNoAltrnateId;

    /** 카드유효기간 */
    @TableField("CARD_VALID_YYMM")
    private String cardValidYymm;

    /** 처리채널코드 */
    @TableField("PRCSSG_CHNL_CD")
    private String prcssgChnlCd;

    /** 처리자번호 */
    @TableField("PRCSSR_NO")
    private String prcssrNo;

    /** 처리일자 */
    @TableField("PRCSSG_DT")
    private String prcssgDt;

    /** 납부수단ID (PK 아님, 인증 성공 후 채움) */
    @TableField("PAY_MEANS_ID")
    private Long payMeansId;

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

    public Long getPayMeansId() {
        return payMeansId;
    }

    public void setPayMeansId(Long payMeansId) {
        this.payMeansId = payMeansId;
    }

    public Long getRealtmAuthentcSeq() {
        return realtmAuthentcSeq;
    }

    public void setRealtmAuthentcSeq(Long realtmAuthentcSeq) {
        this.realtmAuthentcSeq = realtmAuthentcSeq;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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

    public String getPayMeansStatChgCd() {
        return payMeansStatChgCd;
    }

    public void setPayMeansStatChgCd(String payMeansStatChgCd) {
        this.payMeansStatChgCd = payMeansStatChgCd;
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

    public String getBankAcctCd() {
        return bankAcctCd;
    }

    public void setBankAcctCd(String bankAcctCd) {
        this.bankAcctCd = bankAcctCd;
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

    public String getCmsAuthentcResultCd() {
        return cmsAuthentcResultCd;
    }

    public void setCmsAuthentcResultCd(String cmsAuthentcResultCd) {
        this.cmsAuthentcResultCd = cmsAuthentcResultCd;
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

    public String getEdiPayMeansChgCd() {
        return ediPayMeansChgCd;
    }

    public void setEdiPayMeansChgCd(String ediPayMeansChgCd) {
        this.ediPayMeansChgCd = ediPayMeansChgCd;
    }

    public String getEdiPayMeansChgResultCd() {
        return ediPayMeansChgResultCd;
    }

    public void setEdiPayMeansChgResultCd(String ediPayMeansChgResultCd) {
        this.ediPayMeansChgResultCd = ediPayMeansChgResultCd;
    }

    public Long getCardNoAltrnateId() {
        return cardNoAltrnateId;
    }

    public void setCardNoAltrnateId(Long cardNoAltrnateId) {
        this.cardNoAltrnateId = cardNoAltrnateId;
    }

    public String getCardValidYymm() {
        return cardValidYymm;
    }

    public void setCardValidYymm(String cardValidYymm) {
        this.cardValidYymm = cardValidYymm;
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
