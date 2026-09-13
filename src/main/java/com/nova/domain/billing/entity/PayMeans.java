package com.nova.domain.billing.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 납부수단 (pay_means)
 *
 * 고객이 등록한 실제 결제 수단 정보를 관리한다.
 * 납부방법코드에 따라 은행(계좌), 카드, 간편결제로 구분되며
 * 금융정보(계좌번호, 카드번호 등)는 이 엔티티에서 중앙 관리한다.
 *
 * 납부계정(PayAcct)이 이 수단을 참조하는 구조 (pay_means : pay_acct = 1 : N)
 */
@Entity
@Table(name = "pay_means",
        indexes = {
                @Index(name = "idx_pay_means_tenant_cust", columnList = "tenant_id, customer_no"),
                @Index(name = "idx_pay_means_method", columnList = "pay_method_cd")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayMeans {

    /** 납부수단ID (PK) */
    @Id
    @Column(name = "pay_means_id", length = 20, nullable = false)
    private String payMeansId;

    /** 테넌트ID (멀티테넌트 필수) */
    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    /** 고객번호 */
    @Column(name = "customer_no", length = 20, nullable = false)
    private String customerNo;

    /**
     * 납부방법코드
     * 은행(BANK) / 카드(CARD) / 간편결제(SIMPLE_PAY)
     */
    @Column(name = "pay_method_cd", length = 20, nullable = false)
    private String payMethodCd;

    /**
     * 납부수단유형코드
     * 은행: 보통예금(CHK) / 저축예금(SAV) 등
     * 카드: 신용(CREDIT) / 체크(CHECK) 등
     * 간편결제: Simple Pay / CMS O-Pay 등
     */
    @Column(name = "pay_means_type_cd", length = 20)
    private String payMeansTypeCd;

    /** 납부기관코드 (은행코드 또는 카드사코드) */
    @Column(name = "pay_inst_cd", length = 10)
    private String payInstCd;

    /** 납부기관명 */
    @Column(name = "pay_inst_nm", length = 100)
    private String payInstNm;

    /**
     * 계좌/카드 대체ID
     * 실제 계좌번호·카드번호는 암호화 후 별도 보안 저장소에 관리하며
     * 이 필드는 참조용 대체 식별자만 보관한다.
     */
    @Column(name = "acct_card_sub_id", length = 50)
    private String acctCardSubId;

    /** 예금주명 / 카드주명 */
    @Column(name = "holder_nm", length = 100)
    private String holderNm;

    /** 예금주명 (영문) */
    @Column(name = "holder_nm_eng", length = 100)
    private String holderNmEng;

    /**
     * 인출유형상세코드
     * EDI+FB / FB Only / EDI Only / CMS Only / Simple Pay / CMS O-Pay
     */
    @Column(name = "wdraw_type_dtl_cd", length = 20)
    private String wdrawTypeDtlCd;

    /** 자동납부 동의여부 */
    @Column(name = "auto_pay_agr_yn", length = 1, nullable = false)
    private String autoPayAgrYn = "N";

    /** 자동납부 신청일자 (YYYYMMDD) */
    @Column(name = "auto_pay_appl_dt", length = 8)
    private String autoPayApplDt;

    /** 자동납부 해지일자 (YYYYMMDD) */
    @Column(name = "auto_pay_canc_dt", length = 8)
    private String autoPayCancDt;

    /** 유효기간 시작일자 (YYYYMMDD) */
    @Column(name = "valid_start_dt", length = 8)
    private String validStartDt;

    /** 유효기간 종료일자 (YYYYMMDD) */
    @Column(name = "valid_end_dt", length = 8)
    private String validEndDt;

    /** 사용여부 */
    @Column(name = "use_yn", length = 1, nullable = false)
    private String useYn = "Y";

    /** 요청사유 */
    @Column(name = "req_reason", length = 200)
    private String reqReason;

    /** 등록일시 (시스템속성) */
    @CreationTimestamp
    @Column(name = "reg_dt", nullable = false, updatable = false)
    private LocalDateTime regDt;

    /** 수정일시 (시스템속성) */
    @UpdateTimestamp
    @Column(name = "upd_dt")
    private LocalDateTime updDt;

    /** 등록자ID (시스템속성) */
    @Column(name = "reg_usr_id", length = 20)
    private String regUsrId;

    /** 수정자ID (시스템속성) */
    @Column(name = "upd_usr_id", length = 20)
    private String updUsrId;

    @Builder
    public PayMeans(String payMeansId, String tenantId, String customerNo,
                    String payMethodCd, String payMeansTypeCd,
                    String payInstCd, String payInstNm,
                    String acctCardSubId, String holderNm, String holderNmEng,
                    String wdrawTypeDtlCd, String autoPayAgrYn,
                    String autoPayApplDt, String autoPayCancDt,
                    String validStartDt, String validEndDt,
                    String useYn, String reqReason,
                    String regUsrId, String updUsrId) {
        this.payMeansId = payMeansId;
        this.tenantId = tenantId;
        this.customerNo = customerNo;
        this.payMethodCd = payMethodCd;
        this.payMeansTypeCd = payMeansTypeCd;
        this.payInstCd = payInstCd;
        this.payInstNm = payInstNm;
        this.acctCardSubId = acctCardSubId;
        this.holderNm = holderNm;
        this.holderNmEng = holderNmEng;
        this.wdrawTypeDtlCd = wdrawTypeDtlCd;
        this.autoPayAgrYn = (autoPayAgrYn != null) ? autoPayAgrYn : "N";
        this.autoPayApplDt = autoPayApplDt;
        this.autoPayCancDt = autoPayCancDt;
        this.validStartDt = validStartDt;
        this.validEndDt = validEndDt;
        this.useYn = (useYn != null) ? useYn : "Y";
        this.reqReason = reqReason;
        this.regUsrId = regUsrId;
        this.updUsrId = updUsrId;
    }

    // ── 비즈니스 메서드 ──────────────────────────────────────────────

    /** 자동납부 신청 처리 */
    public void applyAutoPay(String applyDate, String reason) {
        this.autoPayAgrYn = "Y";
        this.autoPayApplDt = applyDate;
        this.reqReason = reason;
    }

    /** 자동납부 해지 처리 */
    public void cancelAutoPay(String cancelDate, String reason) {
        this.autoPayAgrYn = "N";
        this.autoPayCancDt = cancelDate;
        this.reqReason = reason;
    }

    /** 사용 중지 */
    public void disable(String updUsrId) {
        this.useYn = "N";
        this.updUsrId = updUsrId;
    }
}
