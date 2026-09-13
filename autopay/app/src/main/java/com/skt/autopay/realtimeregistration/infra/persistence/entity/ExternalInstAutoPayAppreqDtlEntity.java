package com.skt.autopay.realtimeregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 외부기관자동납부신청상세 (EXTERNAL_INST_AUTO_PAY_APPREQ_DTL) 엔티티
 *
 * <p>entity-definitions.md 기준. 한글 논리명을 영문 물리명으로 변환하여 생성.
 * 외부기관코드+자동납부신청순번(FK) + 상세순번(PK 구성).
 */
@TableName("EXTERNAL_INST_AUTO_PAY_APPREQ_DTL")
public class ExternalInstAutoPayAppreqDtlEntity {

    /** 외부기관코드 (FK) */
    @TableField("EXTNL_INST_CD")
    private String extnlInstCd;

    /** 자동납부신청순번 (FK) */
    @TableField("AUTO_PAY_APPREQ_SEQ")
    private Long autoPayAppreqSeq;

    /** 테넌트ID */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 상세순번 */
    @TableField("DTL_SEQ")
    private Long dtlSeq;

    /** 납부방법코드 */
    @TableField("PAY_MTHD_CD")
    private String payMthdCd;

    /** 상세처리결과 */
    @TableField("DTL_PRCSSG_RESULT")
    private String dtlPrcssgResult;

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

    public Long getDtlSeq() {
        return dtlSeq;
    }

    public void setDtlSeq(Long dtlSeq) {
        this.dtlSeq = dtlSeq;
    }

    public String getPayMthdCd() {
        return payMthdCd;
    }

    public void setPayMthdCd(String payMthdCd) {
        this.payMthdCd = payMthdCd;
    }

    public String getDtlPrcssgResult() {
        return dtlPrcssgResult;
    }

    public void setDtlPrcssgResult(String dtlPrcssgResult) {
        this.dtlPrcssgResult = dtlPrcssgResult;
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
