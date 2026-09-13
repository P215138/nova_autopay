package com.skt.autopay.realtimeregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 외부기관자동납부신청 (EXTERNAL_INST_AUTO_PAY_APPREQ) 엔티티
 *
 * <p>entity-definitions.md 기준. 한글 논리명을 영문 물리명으로 변환하여 생성.
 * PK 복합키: 외부기관코드+자동납부신청순번+테넌트ID.
 */
@TableName("EXTERNAL_INST_AUTO_PAY_APPREQ")
public class ExternalInstAutoPayAppreqEntity {

    /** 외부기관코드 (PK) */
    @TableField("EXTNL_INST_CD")
    private String extnlInstCd;

    /** 자동납부신청순번 (PK) */
    @TableField("AUTO_PAY_APPREQ_SEQ")
    private Long autoPayAppreqSeq;

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

    /** 외부기관명 */
    @TableField("EXTNL_INST_NM")
    private String extnlInstNm;

    /** 외부기관처리일자 */
    @TableField("EXTNL_INST_PRCSSG_DT")
    private String extnlInstPrcssgDt;

    /** 외부기관처리결과코드 */
    @TableField("EXTNL_INST_PRCSSG_RESULT_CD")
    private String extnlInstPrcssgResultCd;

    /** 외부기관처리결과메시지 */
    @TableField("EXTNL_INST_PRCSSG_RESULT_MSG")
    private String extnlInstPrcssgResultMsg;

    /** 외부인증키 */
    @TableField("EXTNL_AUTHENTC_KEY")
    private String extnlAuthentcKey;

    /** 외부인증결과코드 */
    @TableField("EXTNL_AUTHENTC_RESULT_CD")
    private String extnlAuthentcResultCd;

    /** 외부인증결과메시지 */
    @TableField("EXTNL_AUTHENTC_RESULT_MSG")
    private String extnlAuthentcResultMsg;

    /** 납부기관코드 */
    @TableField("PAY_INST_CD")
    private String payInstCd;

    /** 납부기관계좌코드 */
    @TableField("PAY_INST_ACCT_CD")
    private String payInstAcctCd;

    /** 납부기관카드코드 */
    @TableField("PAY_INST_CARD_CD")
    private String payInstCardCd;

    /** 납부기관간편결제코드 */
    @TableField("PAY_INST_SIMPLE_PAY_CD")
    private String payInstSimplePayCd;

    /** 계좌카드대체ID */
    @TableField("BANKACCT_CARD_ALTRNATE_ID")
    private Long bankacctCardAltrnateId;

    /** EDI서비스코드 */
    @TableField("EDI_SVC_CD")
    private String ediSvcCd;

    /** EDI인증번호 */
    @TableField("EDI_AUTHENTC_NO")
    private String ediAuthentcNo;

    /** EDI인증결과코드 */
    @TableField("EDI_AUTHENTC_RESULT_CD")
    private String ediAuthentcResultCd;

    /** EDI인증결과메시지 */
    @TableField("EDI_AUTHENTC_RESULT_MSG")
    private String ediAuthentcResultMsg;

    /** FB서비스코드 */
    @TableField("FB_SVC_CD")
    private String fbSvcCd;

    /** FB인증번호 */
    @TableField("FB_AUTHENTC_NO")
    private String fbAuthentcNo;

    /** FB인증결과코드 */
    @TableField("FB_AUTHENTC_RESULT_CD")
    private String fbAuthentcResultCd;

    /** FB인증결과메시지 */
    @TableField("FB_AUTHENTC_RESULT_MSG")
    private String fbAuthentcResultMsg;

    /** 간편결제인증번호 */
    @TableField("SIMPLE_PAY_AUTHENTC_NO")
    private String simplePayAuthentcNo;

    /** 간편결제인증결과코드 */
    @TableField("SIMPLE_PAY_AUTHENTC_RESULT_CD")
    private String simplePayAuthentcResultCd;

    /** 카드승인번호 */
    @TableField("CARD_APPRV_NO")
    private String cardApprvNo;

    /** 카드승인금액 */
    @TableField("CARD_APPRV_AMT")
    private BigDecimal cardApprvAmt;

    /** 처리채널코드 */
    @TableField("PRCSSG_CHNL_CD")
    private String prcssgChnlCd;

    /** 처리자번호 */
    @TableField("PRCSSR_NO")
    private String prcssrNo;

    /** 처리일자 */
    @TableField("PRCSSG_DT")
    private String prcssgDt;

    /** 납부계정번호 */
    @TableField("PAY_ACCT_NO")
    private Long payAcctNo;

    /** 납부수단ID */
    @TableField("PAY_MEANS_ID")
    private Long payMeansId;

    /** 자동납부등록일자 */
    @TableField("AUTO_PAY_REGIST_DT")
    private String autoPayRegistDt;

    /** 신청구분코드 */
    @TableField("APPREQ_CATG_CD")
    private String appreqCatgCd;

    /** 처리기관코드 */
    @TableField("PRCSSG_INST_CD")
    private String prcssgInstCd;

    /** 처리기관명 */
    @TableField("PRCSSG_INST_NM")
    private String prcssgInstNm;

    /** 외부시스템연동코드 */
    @TableField("EXTNL_SYS_LINK_CD")
    private String extnlSysLinkCd;

    /** 외부시스템연동결과코드 */
    @TableField("EXTNL_SYS_LINK_RESULT_CD")
    private String extnlSysLinkResultCd;

    /** 재처리여부 */
    @TableField("RE_PRCSSG_YN")
    private String rePrcssgYn;

    /** 재처리횟수 */
    @TableField("RE_PRCSSG_CNT")
    private Integer rePrcssgCnt;

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

    public String getExtnlInstCd() {
        return extnlInstCd;
    }

    public void setExtnlInstCd(String extnlInstCd) {
        this.extnlInstCd = extnlInstCd;
    }

    public Long getAutoPayAppreqSeq() {
        return autoPayAppreqSeq;
    }

    public void setAutoPayAppreqSeq(Long autoPayAppreqSeq) {
        this.autoPayAppreqSeq = autoPayAppreqSeq;
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

    public String getExtnlInstNm() {
        return extnlInstNm;
    }

    public void setExtnlInstNm(String extnlInstNm) {
        this.extnlInstNm = extnlInstNm;
    }

    public String getExtnlInstPrcssgDt() {
        return extnlInstPrcssgDt;
    }

    public void setExtnlInstPrcssgDt(String extnlInstPrcssgDt) {
        this.extnlInstPrcssgDt = extnlInstPrcssgDt;
    }

    public String getExtnlInstPrcssgResultCd() {
        return extnlInstPrcssgResultCd;
    }

    public void setExtnlInstPrcssgResultCd(String extnlInstPrcssgResultCd) {
        this.extnlInstPrcssgResultCd = extnlInstPrcssgResultCd;
    }

    public String getExtnlInstPrcssgResultMsg() {
        return extnlInstPrcssgResultMsg;
    }

    public void setExtnlInstPrcssgResultMsg(String extnlInstPrcssgResultMsg) {
        this.extnlInstPrcssgResultMsg = extnlInstPrcssgResultMsg;
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

    public String getPayInstCardCd() {
        return payInstCardCd;
    }

    public void setPayInstCardCd(String payInstCardCd) {
        this.payInstCardCd = payInstCardCd;
    }

    public String getPayInstSimplePayCd() {
        return payInstSimplePayCd;
    }

    public void setPayInstSimplePayCd(String payInstSimplePayCd) {
        this.payInstSimplePayCd = payInstSimplePayCd;
    }

    public Long getBankacctCardAltrnateId() {
        return bankacctCardAltrnateId;
    }

    public void setBankacctCardAltrnateId(Long bankacctCardAltrnateId) {
        this.bankacctCardAltrnateId = bankacctCardAltrnateId;
    }

    public String getEdiSvcCd() {
        return ediSvcCd;
    }

    public void setEdiSvcCd(String ediSvcCd) {
        this.ediSvcCd = ediSvcCd;
    }

    public String getEdiAuthentcNo() {
        return ediAuthentcNo;
    }

    public void setEdiAuthentcNo(String ediAuthentcNo) {
        this.ediAuthentcNo = ediAuthentcNo;
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

    public String getFbSvcCd() {
        return fbSvcCd;
    }

    public void setFbSvcCd(String fbSvcCd) {
        this.fbSvcCd = fbSvcCd;
    }

    public String getFbAuthentcNo() {
        return fbAuthentcNo;
    }

    public void setFbAuthentcNo(String fbAuthentcNo) {
        this.fbAuthentcNo = fbAuthentcNo;
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

    public String getSimplePayAuthentcResultCd() {
        return simplePayAuthentcResultCd;
    }

    public void setSimplePayAuthentcResultCd(String simplePayAuthentcResultCd) {
        this.simplePayAuthentcResultCd = simplePayAuthentcResultCd;
    }

    public String getCardApprvNo() {
        return cardApprvNo;
    }

    public void setCardApprvNo(String cardApprvNo) {
        this.cardApprvNo = cardApprvNo;
    }

    public BigDecimal getCardApprvAmt() {
        return cardApprvAmt;
    }

    public void setCardApprvAmt(BigDecimal cardApprvAmt) {
        this.cardApprvAmt = cardApprvAmt;
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

    public Long getPayAcctNo() {
        return payAcctNo;
    }

    public void setPayAcctNo(Long payAcctNo) {
        this.payAcctNo = payAcctNo;
    }

    public Long getPayMeansId() {
        return payMeansId;
    }

    public void setPayMeansId(Long payMeansId) {
        this.payMeansId = payMeansId;
    }

    public String getAutoPayRegistDt() {
        return autoPayRegistDt;
    }

    public void setAutoPayRegistDt(String autoPayRegistDt) {
        this.autoPayRegistDt = autoPayRegistDt;
    }

    public String getAppreqCatgCd() {
        return appreqCatgCd;
    }

    public void setAppreqCatgCd(String appreqCatgCd) {
        this.appreqCatgCd = appreqCatgCd;
    }

    public String getPrcssgInstCd() {
        return prcssgInstCd;
    }

    public void setPrcssgInstCd(String prcssgInstCd) {
        this.prcssgInstCd = prcssgInstCd;
    }

    public String getPrcssgInstNm() {
        return prcssgInstNm;
    }

    public void setPrcssgInstNm(String prcssgInstNm) {
        this.prcssgInstNm = prcssgInstNm;
    }

    public String getExtnlSysLinkCd() {
        return extnlSysLinkCd;
    }

    public void setExtnlSysLinkCd(String extnlSysLinkCd) {
        this.extnlSysLinkCd = extnlSysLinkCd;
    }

    public String getExtnlSysLinkResultCd() {
        return extnlSysLinkResultCd;
    }

    public void setExtnlSysLinkResultCd(String extnlSysLinkResultCd) {
        this.extnlSysLinkResultCd = extnlSysLinkResultCd;
    }

    public String getRePrcssgYn() {
        return rePrcssgYn;
    }

    public void setRePrcssgYn(String rePrcssgYn) {
        this.rePrcssgYn = rePrcssgYn;
    }

    public Integer getRePrcssgCnt() {
        return rePrcssgCnt;
    }

    public void setRePrcssgCnt(Integer rePrcssgCnt) {
        this.rePrcssgCnt = rePrcssgCnt;
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
