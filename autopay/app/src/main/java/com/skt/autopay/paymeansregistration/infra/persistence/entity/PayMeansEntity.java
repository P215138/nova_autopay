package com.skt.autopay.paymeansregistration.infra.persistence.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * 납부수단 (PAY_MEANS) 엔티티
 *
 * <p>테이블 정의서(엑셀) 기반. 이미지 판독으로 생성되어 컬럼명/타입 자리수에 오차가 있을 수 있음.
 */
@TableName("PAY_MEANS")
public class PayMeansEntity {

    /** 납부수단번호 (PK) */
    @TableId(value = "PAY_MEANS_NO", type = IdType.INPUT)
    private Long payMeansNo;

    /** 테넌트ID */
    @TableField("TENANT_ID")
    private String tenantId;

    /** 서브테넌트ID */
    @TableField("SUB_TENANT_ID")
    private String subTenantId;

    /** 납부수단명 암호화 */
    @TableField("PAY_MEANS_NM_ENCRYPT")
    private String payMeansNmEncrypt;

    /** 납부수단상태코드 (01 등록중 / 02 인증대기 / 10 활성 / 20 정지 / 30 해지 / 40 종료) */
    @TableField("PAY_MEANS_STAT_CD")
    private String payMeansStatCd;

    /** 납부수단변경사유코드 */
    @TableField("PAY_MEANS_CHG_REASON_CD")
    private String payMeansChgReasonCd;

    /** 고객번호 */
    @TableField("CUSTOMER_NO")
    private String customerNo;

    /** 고객연락처ID */
    @TableField("CUSTOMER_CNTCINFO_ID")
    private Long customerCntcinfoId;

    /** 연락처전화번호 암호화 */
    @TableField("CNTC_PHNO_ENCRYPT")
    private String cntcPhnoEncrypt;

    /** 납부수단유형코드 (01 은행 / 02 카드 / 05 간편결제) */
    @TableField("PAY_MEANS_TYPE_CD")
    private String payMeansTypeCd;

    /** 금융기관코드 */
    @TableField("FINC_INST_CD")
    private String fincInstCd;

    /** 발급카드사코드 */
    @TableField("ISSUNC_CARDCO_CD")
    private String issuncCardcoCd;

    /** 계좌카드대체ID */
    @TableField("BANKACCT_CARD_ALTRNATE_ID")
    private Long bankacctCardAltrnateId;

    /** 카드유효연월 */
    @TableField("CARD_VALID_YYMM")
    private String cardValidYymm;

    /** 해외카드여부 */
    @TableField("OVERSEAS_CARD_YN")
    private String overseasCardYn;

    /** 계좌카드소유자명 암호화 */
    @TableField("BANKACCT_CARDHDR_NM_ENCRYPT")
    private String bankacctCardhdrNmEncrypt;

    /** 고객번호식별구분코드 */
    @TableField("CUSTOMER_NO_IDNT_CATG_CD")
    private String customerNoIdntCatgCd;

    /** 주민법인사업자대체ID */
    @TableField("RSDT_CORP_BIZOPR_ALTRNATE_ID")
    private String rsdtCorpBizoprAltrnateId;

    /** 주민등록번호확인여부 */
    @TableField("RRN_CNFRM_YN")
    private String rrnCnfrmYn;

    /** 계좌카드주고객관계코드 */
    @TableField("BANKACCT_CARDHDR_CUSTOMER_REL_CD")
    private String bankacctCardhdrCustomerRelCd;

    /** 대리인주민법인사업자대체ID */
    @TableField("AGENT_RSDT_CORP_BIZOPR_ALTRNATE_ID")
    private String agentRsdtCorpBizoprAltrnateId;

    /** 대리인명 암호화 */
    @TableField("AGENT_NM_ENCRYPT")
    private String agentNmEncrypt;

    /** 대리인고객관계코드 */
    @TableField("AGENT_CUSTOMER_REL_CD")
    private String agentCustomerRelCd;

    /** 고객역할관계코드 */
    @TableField("CUSTOMER_ROLE_REL_CD")
    private String customerRoleRelCd;

    /** 대리인연락처전화번호 암호화 */
    @TableField("AGENT_CNTC_PHNO_ENCRYPT")
    private String agentCntcPhnoEncrypt;

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

    public Long getPayMeansNo() {
        return payMeansNo;
    }

    public void setPayMeansNo(Long payMeansNo) {
        this.payMeansNo = payMeansNo;
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

    public String getPayMeansNmEncrypt() {
        return payMeansNmEncrypt;
    }

    public void setPayMeansNmEncrypt(String payMeansNmEncrypt) {
        this.payMeansNmEncrypt = payMeansNmEncrypt;
    }

    public String getPayMeansStatCd() {
        return payMeansStatCd;
    }

    public void setPayMeansStatCd(String payMeansStatCd) {
        this.payMeansStatCd = payMeansStatCd;
    }

    public String getPayMeansChgReasonCd() {
        return payMeansChgReasonCd;
    }

    public void setPayMeansChgReasonCd(String payMeansChgReasonCd) {
        this.payMeansChgReasonCd = payMeansChgReasonCd;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Long getCustomerCntcinfoId() {
        return customerCntcinfoId;
    }

    public void setCustomerCntcinfoId(Long customerCntcinfoId) {
        this.customerCntcinfoId = customerCntcinfoId;
    }

    public String getCntcPhnoEncrypt() {
        return cntcPhnoEncrypt;
    }

    public void setCntcPhnoEncrypt(String cntcPhnoEncrypt) {
        this.cntcPhnoEncrypt = cntcPhnoEncrypt;
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

    public String getIssuncCardcoCd() {
        return issuncCardcoCd;
    }

    public void setIssuncCardcoCd(String issuncCardcoCd) {
        this.issuncCardcoCd = issuncCardcoCd;
    }

    public Long getBankacctCardAltrnateId() {
        return bankacctCardAltrnateId;
    }

    public void setBankacctCardAltrnateId(Long bankacctCardAltrnateId) {
        this.bankacctCardAltrnateId = bankacctCardAltrnateId;
    }

    public String getCardValidYymm() {
        return cardValidYymm;
    }

    public void setCardValidYymm(String cardValidYymm) {
        this.cardValidYymm = cardValidYymm;
    }

    public String getOverseasCardYn() {
        return overseasCardYn;
    }

    public void setOverseasCardYn(String overseasCardYn) {
        this.overseasCardYn = overseasCardYn;
    }

    public String getBankacctCardhdrNmEncrypt() {
        return bankacctCardhdrNmEncrypt;
    }

    public void setBankacctCardhdrNmEncrypt(String bankacctCardhdrNmEncrypt) {
        this.bankacctCardhdrNmEncrypt = bankacctCardhdrNmEncrypt;
    }

    public String getCustomerNoIdntCatgCd() {
        return customerNoIdntCatgCd;
    }

    public void setCustomerNoIdntCatgCd(String customerNoIdntCatgCd) {
        this.customerNoIdntCatgCd = customerNoIdntCatgCd;
    }

    public String getRsdtCorpBizoprAltrnateId() {
        return rsdtCorpBizoprAltrnateId;
    }

    public void setRsdtCorpBizoprAltrnateId(String rsdtCorpBizoprAltrnateId) {
        this.rsdtCorpBizoprAltrnateId = rsdtCorpBizoprAltrnateId;
    }

    public String getRrnCnfrmYn() {
        return rrnCnfrmYn;
    }

    public void setRrnCnfrmYn(String rrnCnfrmYn) {
        this.rrnCnfrmYn = rrnCnfrmYn;
    }

    public String getBankacctCardhdrCustomerRelCd() {
        return bankacctCardhdrCustomerRelCd;
    }

    public void setBankacctCardhdrCustomerRelCd(String bankacctCardhdrCustomerRelCd) {
        this.bankacctCardhdrCustomerRelCd = bankacctCardhdrCustomerRelCd;
    }

    public String getAgentRsdtCorpBizoprAltrnateId() {
        return agentRsdtCorpBizoprAltrnateId;
    }

    public void setAgentRsdtCorpBizoprAltrnateId(String agentRsdtCorpBizoprAltrnateId) {
        this.agentRsdtCorpBizoprAltrnateId = agentRsdtCorpBizoprAltrnateId;
    }

    public String getAgentNmEncrypt() {
        return agentNmEncrypt;
    }

    public void setAgentNmEncrypt(String agentNmEncrypt) {
        this.agentNmEncrypt = agentNmEncrypt;
    }

    public String getAgentCustomerRelCd() {
        return agentCustomerRelCd;
    }

    public void setAgentCustomerRelCd(String agentCustomerRelCd) {
        this.agentCustomerRelCd = agentCustomerRelCd;
    }

    public String getCustomerRoleRelCd() {
        return customerRoleRelCd;
    }

    public void setCustomerRoleRelCd(String customerRoleRelCd) {
        this.customerRoleRelCd = customerRoleRelCd;
    }

    public String getAgentCntcPhnoEncrypt() {
        return agentCntcPhnoEncrypt;
    }

    public void setAgentCntcPhnoEncrypt(String agentCntcPhnoEncrypt) {
        this.agentCntcPhnoEncrypt = agentCntcPhnoEncrypt;
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
