package com.skt.autopay.service.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 서비스 (SERVICE) 엔티티 — 이동전화 서비스.
 *
 * <p>청구계정(BILLING_ACCOUNT)과 BILLING_ACCOUNT_NO 로 연동.
 * 서비스관리번호는 1로 시작하는 10자리 애플리케이션 채번.
 * 상태코드: AC 사용중 / TG 해지 / SP 정지.
 */
@TableName("SERVICE")
public class ServiceEntity {

    /** 서비스관리번호 (PK, 1로 시작 10자리) */
    @TableId(value = "SERVICE_MGMT_NO", type = IdType.INPUT)
    private String serviceMgmtNo;

    /** 테넌트ID */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 청구계정번호 (BILLING_ACCOUNT 연동) */
    @TableField("BILLING_ACCOUNT_NO")
    private String billingAccountNo;

    /** 서비스번호대체ID */
    @TableField("SERVICE_NO_ALTRNATE_ID")
    private String serviceNoAltrnateId;

    /** 서비스상태코드 (AC 사용중/TG 해지/SP 정지) */
    @TableField("SERVICE_STAT_CD")
    private String serviceStatCd;

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

    public String getServiceMgmtNo() {
        return serviceMgmtNo;
    }

    public void setServiceMgmtNo(String serviceMgmtNo) {
        this.serviceMgmtNo = serviceMgmtNo;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBillingAccountNo() {
        return billingAccountNo;
    }

    public void setBillingAccountNo(String billingAccountNo) {
        this.billingAccountNo = billingAccountNo;
    }

    public String getServiceNoAltrnateId() {
        return serviceNoAltrnateId;
    }

    public void setServiceNoAltrnateId(String serviceNoAltrnateId) {
        this.serviceNoAltrnateId = serviceNoAltrnateId;
    }

    public String getServiceStatCd() {
        return serviceStatCd;
    }

    public void setServiceStatCd(String serviceStatCd) {
        this.serviceStatCd = serviceStatCd;
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
