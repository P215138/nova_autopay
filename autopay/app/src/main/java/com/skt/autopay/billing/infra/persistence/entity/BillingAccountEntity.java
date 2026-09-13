package com.skt.autopay.billing.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 청구계정 (BILLING_ACCOUNT) 엔티티
 *
 * <p>nova BillingAccount(JPA) 참조. 1 청구계정 : N 납부계정 구조.
 */
@TableName("BILLING_ACCOUNT")
public class BillingAccountEntity {

    /** 청구계정번호 (PK) */
    @TableId(value = "BILLING_ACCOUNT_NO", type = IdType.INPUT)
    private String billingAccountNo;

    /** 테넌트ID */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 고객번호 */
    @TableField("CUSTOMER_NO")
    private String customerNo;

    /** 납부구분코드 */
    @TableField("PAY_CL_CODE")
    private String payClCode;

    /** 청구주기코드 */
    @TableField("BILLING_CYCLE_CODE")
    private String billingCycleCode;

    /** 사용여부 */
    @TableField("USE_YN")
    private String useYn;

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

    public String getBillingAccountNo() {
        return billingAccountNo;
    }

    public void setBillingAccountNo(String billingAccountNo) {
        this.billingAccountNo = billingAccountNo;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getPayClCode() {
        return payClCode;
    }

    public void setPayClCode(String payClCode) {
        this.payClCode = payClCode;
    }

    public String getBillingCycleCode() {
        return billingCycleCode;
    }

    public void setBillingCycleCode(String billingCycleCode) {
        this.billingCycleCode = billingCycleCode;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
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
