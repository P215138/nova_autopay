package com.skt.autopay.realtimeregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 납부계정인증(WTT) (PAY_ACCT_AUTHENTC_WTT) 엔티티
 *
 * <p>entity-definitions.md 기준. 한글 논리명을 영문 물리명으로 변환하여 생성.
 * PK 복합키: 세대+납부계정번호+납부수단순번+테넌트ID.
 */
@TableName("PAY_ACCT_AUTHENTC_WTT")
public class PayAcctAuthentcWttEntity {

    /** 세대 (PK) */
    @TableField("HSHLD")
    private String hshld;

    /** 납부계정번호 (PK) */
    @TableField("PAY_ACCT_NO")
    private Long payAcctNo;

    /** 납부수단순번 (PK) */
    @TableField("PAY_MEANS_SEQ")
    private Long payMeansSeq;

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

    /** NGM내용코드 */
    @TableField("NGM_CNTNT_CD")
    private String ngmCntntCd;

    /** 시작일시 */
    @TableField("START_DTM")
    private LocalDateTime startDtm;

    /** 종료일시 */
    @TableField("END_DTM")
    private LocalDateTime endDtm;

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

    public String getHshld() {
        return hshld;
    }

    public void setHshld(String hshld) {
        this.hshld = hshld;
    }

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

    public String getNgmCntntCd() {
        return ngmCntntCd;
    }

    public void setNgmCntntCd(String ngmCntntCd) {
        this.ngmCntntCd = ngmCntntCd;
    }

    public LocalDateTime getStartDtm() {
        return startDtm;
    }

    public void setStartDtm(LocalDateTime startDtm) {
        this.startDtm = startDtm;
    }

    public LocalDateTime getEndDtm() {
        return endDtm;
    }

    public void setEndDtm(LocalDateTime endDtm) {
        this.endDtm = endDtm;
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
