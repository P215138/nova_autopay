package com.skt.autopay.paymeansregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 계좌카드정보 금고 (BANKACCT_CARD_INFO) 엔티티.
 *
 * <p>계좌/카드 원본번호를 암호화하여 별도 보관. PAY_MEANS에는 대체ID만 저장한다.
 * 대체ID는 원본번호 기반 결정적 값(같은 번호 → 같은 대체ID)으로 중복 판단에 사용된다.
 */
@TableName("BANKACCT_CARD_INFO")
public class BankacctCardInfoEntity {

    /** 계좌카드대체ID (PK, 결정적 채번) */
    @TableId(value = "BANKACCT_CARD_ALTRNATE_ID", type = IdType.INPUT)
    private Long bankacctCardAltrnateId;

    /** 테넌트ID */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 계좌/카드번호 암호화(AES) */
    @TableField("ACCT_CARD_NO_ENCRYPT")
    private String acctCardNoEncrypt;

    /** 납부수단유형코드 */
    @TableField("PAY_MEANS_TYPE_CD")
    private String payMeansTypeCd;

    /** 금융기관/카드사 코드 */
    @TableField("FINC_INST_CD")
    private String fincInstCd;

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

    public Long getBankacctCardAltrnateId() {
        return bankacctCardAltrnateId;
    }

    public void setBankacctCardAltrnateId(Long bankacctCardAltrnateId) {
        this.bankacctCardAltrnateId = bankacctCardAltrnateId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getAcctCardNoEncrypt() {
        return acctCardNoEncrypt;
    }

    public void setAcctCardNoEncrypt(String acctCardNoEncrypt) {
        this.acctCardNoEncrypt = acctCardNoEncrypt;
    }

    public String getPayMeansTypeCd() {
        return payMeansTypeCd;
    }

    public void setPayMeansTypeCd(String payMeansTypeCd) {
        this.payMeansTypeCd = payMeansTypeCd;
    }

    public String getFincInstCd() {
        return fincInstCd;
    }

    public void setFincInstCd(String fincInstCd) {
        this.fincInstCd = fincInstCd;
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
