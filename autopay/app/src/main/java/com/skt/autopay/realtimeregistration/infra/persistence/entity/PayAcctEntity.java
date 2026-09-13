package com.skt.autopay.realtimeregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 납부계정 (PAY_ACCT) 엔티티
 *
 * <p>테이블 정의서(엑셀) 기반. 이미지 판독으로 생성되어 컬럼명/타입 자리수/PK 구성에 오차가 있을 수 있음.
 */
@TableName("PAY_ACCT")
public class PayAcctEntity {

    /** 납부계정번호 (PK) */
    @TableId(value = "PAY_ACCT_NO", type = IdType.INPUT)
    private Long payAcctNo;

    /** 테넌트ID (PK) */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 서브테넌트ID */
    @TableField("SUB_TENANT_ID")
    private String subTenantId;

    /** 납부계정명 암호화 */
    @TableField("PAY_ACCT_NM_ENCRYPT")
    private String payAcctNmEncrypt;

    /** 납부계정상태코드 */
    @TableField("PAY_ACCT_STAT_CD")
    private String payAcctStatCd;

    /** 납부구분코드 (PK) */
    @TableField("PAY_CATG_CD")
    private String payCatgCd;

    /** 계약번호 */
    @TableField("CONTRACT_NO")
    private String contractNo;

    /** 청구계정번호 */
    @TableField("INVOICE_ACCT_NO")
    private Long invoiceAcctNo;

    /** 납부방법코드 */
    @TableField("PAY_MTHD_CD")
    private String payMthdCd;

    /** 납부주기코드 */
    @TableField("PAY_CYCLE_CD")
    private String payCycleCd;

    /** 납부주기일코드 */
    @TableField("PAY_CYCLE_DAY_CD")
    private String payCycleDayCd;

    /** 납부수단번호 */
    @TableField("PAY_MEANS_NO")
    private Long payMeansNo;

    /** 예비납부수단번호 (PK) */
    @TableField("RESERVE_PAY_MEANS_NO")
    private Long reservePayMeansNo;

    /** BILLKEY */
    @TableField("BILLKEY")
    private String billkey;

    /** 자동납부할인제외여부 */
    @TableField("AUTO_PAY_DC_EXCLS_YN")
    private String autoPayDcExclsYn;

    /** 분리납부그룹ID */
    @TableField("SEPARAT_PAY_GROUP_ID")
    private String separatPayGroupId;

    /** 분리금액 */
    @TableField("SEPARAT_AMT")
    private BigDecimal separatAmt;

    /** 분리비율 */
    @TableField("SEPARAT_RATIO")
    private BigDecimal separatRatio;

    /** 분리납부대표여부 */
    @TableField("SEPARAT_PAY_REPRSNT_YN")
    private String separatPayReprsntYn;

    /** 접수조직RID */
    @TableField("RCPTN_ORG_RID")
    private String rcptnOrgRid;

    /** 처리조직RID */
    @TableField("PRCSSG_ORG_RID")
    private String prcssgOrgRid;

    /** 지시장조직RID */
    @TableField("DIRSTR_ORG_RID")
    private String dirstrOrgRid;

    /** 판매점조직RID */
    @TableField("SALSTR_ORG_RID")
    private String salstrOrgRid;

    /** 신청자처리RID */
    @TableField("APPLREQ_PRCSSR_RID")
    private String applreqPrcssrRid;

    /** 납부연락문자메시지유형코드 */
    @TableField("PAY_CNTC_TEXTMSG_MSG_TYPE_CD")
    private String payCntcTextmsgMsgTypeCd;

    /** 최초신청일자 */
    @TableField("FIRST_APPLREQ_DT")
    private String firstApplreqDt;

    /** 신청일자 */
    @TableField("APPLREQ_DT")
    private String applreqDt;

    /** 신청취소일자 */
    @TableField("APPLREQ_CANCL_DT")
    private String applreqCanclDt;

    /** 자동납부신청구분코드 */
    @TableField("AUTO_PAY_APPLREQ_CATG_CD")
    private String autoPayApplreqCatgCd;

    /** 자동납부변경사유코드 */
    @TableField("AUTO_PAY_CHG_REASON_CD")
    private String autoPayChgReasonCd;

    /** 자동납부접수구분코드 */
    @TableField("AUTO_PAY_RCPTN_CATG_CD")
    private String autoPayRcptnCatgCd;

    /** 자동납부접수채널구분코드 */
    @TableField("AUTO_PAY_RCPTN_CHNL_CATG_CD")
    private String autoPayRcptnChnlCatgCd;

    /** 납부계정인증요청일자 */
    @TableField("PAY_ACCT_AUTHENTC_REQ_DT")
    private String payAcctAuthentcReqDt;

    /** 자동납부인증결과코드 */
    @TableField("AUTO_PAY_AUTHENTC_RESULT_CD")
    private String autoPayAuthentcResultCd;

    /** CMS여부 */
    @TableField("CMS_YN")
    private String cmsYn;

    /** FB여부 */
    @TableField("FB_YN")
    private String fbYn;

    /** EDI여부 */
    @TableField("EDI_YN")
    private String ediYn;

    /** CMSFB요청일자 */
    @TableField("CMS_FB_REQ_DT")
    private String cmsFbReqDt;

    /** EDI자동납부접수구분코드 */
    @TableField("EDI_AUTO_PAY_RCPTN_CATG_CD")
    private String ediAutoPayRcptnCatgCd;

    /** EDI인증요청일자 */
    @TableField("EDI_AUTHENTC_REQ_DT")
    private String ediAuthentcReqDt;

    /** EDI자동납부인증결과코드 */
    @TableField("EDI_AUTO_PAY_AUTHENTC_RESULT_CD")
    private String ediAutoPayAuthentcResultCd;

    /** EDI적용일자 */
    @TableField("EDI_APPLY_DT")
    private LocalDateTime ediApplyDt;

    /** EDI자동납부신청일시 */
    @TableField("EDI_AUTO_PAY_APPLREQ_DTM")
    private String ediAutoPayApplreqDtm;

    /** 최초인출계획일자 */
    @TableField("FIRST_WDRW_PLAN_DT")
    private String firstWdrwPlanDt;

    /** 최초인출일자 */
    @TableField("FIRST_WDRW_DT")
    private String firstWdrwDt;

    /** 당월차인출여부 */
    @TableField("CURR8TH_WDRW_YN")
    private String curr8thWdrwYn;

    /** 인출TN */
    @TableField("WDRW_TN")
    private Long wdrwTn;

    /** 최종납부계정이력순번 */
    @TableField("FINAL_PAY_ACCT_HIST_SEQNO")
    private Long finalPayAcctHistSeqno;

    /** 납부자번호구분코드 */
    @TableField("PAYER_NO_CATG_CD")
    private String payerNoCatgCd;

    /** 신청서확인바코드번호 */
    @TableField("APPLICHKR_BARCD_NO")
    private String applichkrBarcdNo;

    /** 인출동의증거유형코드 */
    @TableField("WDRW_AGREE_EVIDENCE_TYPE_CD")
    private String wdrwAgreeEvidenceTypeCd;

    /** 인출동의증거키ID */
    @TableField("WDRW_AGREE_EVIDENCE_KEY_ID")
    private String wdrwAgreeEvidenceKeyId;

    /** 증빙이미지키API구분코드 */
    @TableField("EVIDENCE_IMAGE_KEY_API_Y_CATG_CD")
    private String evidenceImageKeyApiYCatgCd;

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(String subTenantId) {
        this.subTenantId = subTenantId;
    }

    public String getPayAcctNmEncrypt() {
        return payAcctNmEncrypt;
    }

    public void setPayAcctNmEncrypt(String payAcctNmEncrypt) {
        this.payAcctNmEncrypt = payAcctNmEncrypt;
    }

    public String getPayAcctStatCd() {
        return payAcctStatCd;
    }

    public void setPayAcctStatCd(String payAcctStatCd) {
        this.payAcctStatCd = payAcctStatCd;
    }

    public String getPayCatgCd() {
        return payCatgCd;
    }

    public void setPayCatgCd(String payCatgCd) {
        this.payCatgCd = payCatgCd;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getInvoiceAcctNo() {
        return invoiceAcctNo;
    }

    public void setInvoiceAcctNo(Long invoiceAcctNo) {
        this.invoiceAcctNo = invoiceAcctNo;
    }

    public String getPayMthdCd() {
        return payMthdCd;
    }

    public void setPayMthdCd(String payMthdCd) {
        this.payMthdCd = payMthdCd;
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

    public Long getPayMeansNo() {
        return payMeansNo;
    }

    public void setPayMeansNo(Long payMeansNo) {
        this.payMeansNo = payMeansNo;
    }

    public Long getReservePayMeansNo() {
        return reservePayMeansNo;
    }

    public void setReservePayMeansNo(Long reservePayMeansNo) {
        this.reservePayMeansNo = reservePayMeansNo;
    }

    public String getBillkey() {
        return billkey;
    }

    public void setBillkey(String billkey) {
        this.billkey = billkey;
    }

    public String getAutoPayDcExclsYn() {
        return autoPayDcExclsYn;
    }

    public void setAutoPayDcExclsYn(String autoPayDcExclsYn) {
        this.autoPayDcExclsYn = autoPayDcExclsYn;
    }

    public String getSeparatPayGroupId() {
        return separatPayGroupId;
    }

    public void setSeparatPayGroupId(String separatPayGroupId) {
        this.separatPayGroupId = separatPayGroupId;
    }

    public BigDecimal getSeparatAmt() {
        return separatAmt;
    }

    public void setSeparatAmt(BigDecimal separatAmt) {
        this.separatAmt = separatAmt;
    }

    public BigDecimal getSeparatRatio() {
        return separatRatio;
    }

    public void setSeparatRatio(BigDecimal separatRatio) {
        this.separatRatio = separatRatio;
    }

    public String getSeparatPayReprsntYn() {
        return separatPayReprsntYn;
    }

    public void setSeparatPayReprsntYn(String separatPayReprsntYn) {
        this.separatPayReprsntYn = separatPayReprsntYn;
    }

    public String getRcptnOrgRid() {
        return rcptnOrgRid;
    }

    public void setRcptnOrgRid(String rcptnOrgRid) {
        this.rcptnOrgRid = rcptnOrgRid;
    }

    public String getPrcssgOrgRid() {
        return prcssgOrgRid;
    }

    public void setPrcssgOrgRid(String prcssgOrgRid) {
        this.prcssgOrgRid = prcssgOrgRid;
    }

    public String getDirstrOrgRid() {
        return dirstrOrgRid;
    }

    public void setDirstrOrgRid(String dirstrOrgRid) {
        this.dirstrOrgRid = dirstrOrgRid;
    }

    public String getSalstrOrgRid() {
        return salstrOrgRid;
    }

    public void setSalstrOrgRid(String salstrOrgRid) {
        this.salstrOrgRid = salstrOrgRid;
    }

    public String getApplreqPrcssrRid() {
        return applreqPrcssrRid;
    }

    public void setApplreqPrcssrRid(String applreqPrcssrRid) {
        this.applreqPrcssrRid = applreqPrcssrRid;
    }

    public String getPayCntcTextmsgMsgTypeCd() {
        return payCntcTextmsgMsgTypeCd;
    }

    public void setPayCntcTextmsgMsgTypeCd(String payCntcTextmsgMsgTypeCd) {
        this.payCntcTextmsgMsgTypeCd = payCntcTextmsgMsgTypeCd;
    }

    public String getFirstApplreqDt() {
        return firstApplreqDt;
    }

    public void setFirstApplreqDt(String firstApplreqDt) {
        this.firstApplreqDt = firstApplreqDt;
    }

    public String getApplreqDt() {
        return applreqDt;
    }

    public void setApplreqDt(String applreqDt) {
        this.applreqDt = applreqDt;
    }

    public String getApplreqCanclDt() {
        return applreqCanclDt;
    }

    public void setApplreqCanclDt(String applreqCanclDt) {
        this.applreqCanclDt = applreqCanclDt;
    }

    public String getAutoPayApplreqCatgCd() {
        return autoPayApplreqCatgCd;
    }

    public void setAutoPayApplreqCatgCd(String autoPayApplreqCatgCd) {
        this.autoPayApplreqCatgCd = autoPayApplreqCatgCd;
    }

    public String getAutoPayChgReasonCd() {
        return autoPayChgReasonCd;
    }

    public void setAutoPayChgReasonCd(String autoPayChgReasonCd) {
        this.autoPayChgReasonCd = autoPayChgReasonCd;
    }

    public String getAutoPayRcptnCatgCd() {
        return autoPayRcptnCatgCd;
    }

    public void setAutoPayRcptnCatgCd(String autoPayRcptnCatgCd) {
        this.autoPayRcptnCatgCd = autoPayRcptnCatgCd;
    }

    public String getAutoPayRcptnChnlCatgCd() {
        return autoPayRcptnChnlCatgCd;
    }

    public void setAutoPayRcptnChnlCatgCd(String autoPayRcptnChnlCatgCd) {
        this.autoPayRcptnChnlCatgCd = autoPayRcptnChnlCatgCd;
    }

    public String getPayAcctAuthentcReqDt() {
        return payAcctAuthentcReqDt;
    }

    public void setPayAcctAuthentcReqDt(String payAcctAuthentcReqDt) {
        this.payAcctAuthentcReqDt = payAcctAuthentcReqDt;
    }

    public String getAutoPayAuthentcResultCd() {
        return autoPayAuthentcResultCd;
    }

    public void setAutoPayAuthentcResultCd(String autoPayAuthentcResultCd) {
        this.autoPayAuthentcResultCd = autoPayAuthentcResultCd;
    }

    public String getCmsYn() {
        return cmsYn;
    }

    public void setCmsYn(String cmsYn) {
        this.cmsYn = cmsYn;
    }

    public String getFbYn() {
        return fbYn;
    }

    public void setFbYn(String fbYn) {
        this.fbYn = fbYn;
    }

    public String getEdiYn() {
        return ediYn;
    }

    public void setEdiYn(String ediYn) {
        this.ediYn = ediYn;
    }

    public String getCmsFbReqDt() {
        return cmsFbReqDt;
    }

    public void setCmsFbReqDt(String cmsFbReqDt) {
        this.cmsFbReqDt = cmsFbReqDt;
    }

    public String getEdiAutoPayRcptnCatgCd() {
        return ediAutoPayRcptnCatgCd;
    }

    public void setEdiAutoPayRcptnCatgCd(String ediAutoPayRcptnCatgCd) {
        this.ediAutoPayRcptnCatgCd = ediAutoPayRcptnCatgCd;
    }

    public String getEdiAuthentcReqDt() {
        return ediAuthentcReqDt;
    }

    public void setEdiAuthentcReqDt(String ediAuthentcReqDt) {
        this.ediAuthentcReqDt = ediAuthentcReqDt;
    }

    public String getEdiAutoPayAuthentcResultCd() {
        return ediAutoPayAuthentcResultCd;
    }

    public void setEdiAutoPayAuthentcResultCd(String ediAutoPayAuthentcResultCd) {
        this.ediAutoPayAuthentcResultCd = ediAutoPayAuthentcResultCd;
    }

    public LocalDateTime getEdiApplyDt() {
        return ediApplyDt;
    }

    public void setEdiApplyDt(LocalDateTime ediApplyDt) {
        this.ediApplyDt = ediApplyDt;
    }

    public String getEdiAutoPayApplreqDtm() {
        return ediAutoPayApplreqDtm;
    }

    public void setEdiAutoPayApplreqDtm(String ediAutoPayApplreqDtm) {
        this.ediAutoPayApplreqDtm = ediAutoPayApplreqDtm;
    }

    public String getFirstWdrwPlanDt() {
        return firstWdrwPlanDt;
    }

    public void setFirstWdrwPlanDt(String firstWdrwPlanDt) {
        this.firstWdrwPlanDt = firstWdrwPlanDt;
    }

    public String getFirstWdrwDt() {
        return firstWdrwDt;
    }

    public void setFirstWdrwDt(String firstWdrwDt) {
        this.firstWdrwDt = firstWdrwDt;
    }

    public String getCurr8thWdrwYn() {
        return curr8thWdrwYn;
    }

    public void setCurr8thWdrwYn(String curr8thWdrwYn) {
        this.curr8thWdrwYn = curr8thWdrwYn;
    }

    public Long getWdrwTn() {
        return wdrwTn;
    }

    public void setWdrwTn(Long wdrwTn) {
        this.wdrwTn = wdrwTn;
    }

    public Long getFinalPayAcctHistSeqno() {
        return finalPayAcctHistSeqno;
    }

    public void setFinalPayAcctHistSeqno(Long finalPayAcctHistSeqno) {
        this.finalPayAcctHistSeqno = finalPayAcctHistSeqno;
    }

    public String getPayerNoCatgCd() {
        return payerNoCatgCd;
    }

    public void setPayerNoCatgCd(String payerNoCatgCd) {
        this.payerNoCatgCd = payerNoCatgCd;
    }

    public String getApplichkrBarcdNo() {
        return applichkrBarcdNo;
    }

    public void setApplichkrBarcdNo(String applichkrBarcdNo) {
        this.applichkrBarcdNo = applichkrBarcdNo;
    }

    public String getWdrwAgreeEvidenceTypeCd() {
        return wdrwAgreeEvidenceTypeCd;
    }

    public void setWdrwAgreeEvidenceTypeCd(String wdrwAgreeEvidenceTypeCd) {
        this.wdrwAgreeEvidenceTypeCd = wdrwAgreeEvidenceTypeCd;
    }

    public String getWdrwAgreeEvidenceKeyId() {
        return wdrwAgreeEvidenceKeyId;
    }

    public void setWdrwAgreeEvidenceKeyId(String wdrwAgreeEvidenceKeyId) {
        this.wdrwAgreeEvidenceKeyId = wdrwAgreeEvidenceKeyId;
    }

    public String getEvidenceImageKeyApiYCatgCd() {
        return evidenceImageKeyApiYCatgCd;
    }

    public void setEvidenceImageKeyApiYCatgCd(String evidenceImageKeyApiYCatgCd) {
        this.evidenceImageKeyApiYCatgCd = evidenceImageKeyApiYCatgCd;
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
