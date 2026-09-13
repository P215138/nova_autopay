package com.nova.domain.billing.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 납부계정 (pay_acct)
 *
 * 청구계정(BillingAccount) 1개에 N개 매핑 가능한 TOBE 핵심 엔티티.
 * 납부수단(PayMeans)을 참조하여 실제 결제 수단과 연결한다.
 *
 * 구조: billing_account (1) ─── (N) pay_acct (N) ─── (1) pay_means
 *
 * - 납부방법코드: 은행(BANK) / 카드(CARD) / 간편결제(SIMPLE_PAY)
 * - 납부금액유형: 전액(FULL) / 지정금액(FIXED) / 잔액(REMAIN)
 * - 선분류부수납구분코드: 선납(PRE) / 분납(PART)
 * - 납부주기코드: 월(M) / 분기(Q) / 년(Y)
 * - 납부주기일코드(2자리): 15 / 21 / 23 / 25 / 26 / 99(말일)
 */
@Entity
@Table(name = "pay_acct",
        indexes = {
                @Index(name = "idx_pay_acct_billing", columnList = "billing_acct_no"),
                @Index(name = "idx_pay_acct_means",   columnList = "pay_means_id"),
                @Index(name = "idx_pay_acct_tenant",  columnList = "tenant_id, billing_acct_no")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayAcct {

    /** 납부계정번호 (PK) */
    @Id
    @Column(name = "pay_acct_no", length = 20, nullable = false)
    private String payAcctNo;

    /** 테넌트ID (멀티테넌트 필수) */
    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    /** 청구계정번호 (FK → billing_account) */
    @Column(name = "billing_acct_no", length = 20, nullable = false)
    private String billingAcctNo;

    /** 납부수단ID (FK → pay_means) */
    @Column(name = "pay_means_id", length = 20, nullable = false)
    private String payMeansId;

    /**
     * 납부방법코드
     * BANK(은행) / CARD(카드) / SIMPLE_PAY(간편결제)
     * pay_means 의 payMethodCd 와 일치해야 한다.
     */
    @Column(name = "pay_method_cd", length = 20, nullable = false)
    private String payMethodCd;

    /**
     * 납부금액유형
     * FULL(전액) / FIXED(지정금액) / REMAIN(잔액)
     */
    @Column(name = "pay_amt_type", length = 10)
    private String payAmtType;

    /**
     * 지정납부금액
     * payAmtType = FIXED 인 경우에만 유효
     */
    @Column(name = "fixed_pay_amt", precision = 15, scale = 0)
    private BigDecimal fixedPayAmt;

    /**
     * 선분류부수납구분코드
     * PRE(선납) / PART(분납)
     */
    @Column(name = "pre_part_pay_cd", length = 10)
    private String prePartPayCd;

    /**
     * 단분코드
     * 납부계정의 분류 단위 코드
     */
    @Column(name = "unit_cd", length = 5)
    private String unitCd;

    /**
     * 납부주기코드
     * M(월) / Q(분기) / Y(년)
     */
    @Column(name = "pay_cycle_cd", length = 5)
    private String payCycleCd;

    /**
     * 납부주기일코드 (2자리)
     * 15 / 21 / 23 / 25 / 26 / 99(말일)
     */
    @Column(name = "pay_cycle_day_cd", length = 2)
    private String payCycleDayCd;

    /**
     * 인출유형상세코드
     * EDI+FB / FB Only / EDI Only / CMS Only / Simple Pay / CMS O-Pay
     */
    @Column(name = "wdraw_type_dtl_cd", length = 20)
    private String wdrawTypeDtlCd;

    /** 우선순위 (청구계정에 납부계정이 N개일 때 적용 순서) */
    @Column(name = "priority", nullable = false)
    private Integer priority = 1;

    /** 유효기간 시작일자 (YYYYMMDD) */
    @Column(name = "valid_start_dt", length = 8)
    private String validStartDt;

    /** 유효기간 종료일자 (YYYYMMDD) */
    @Column(name = "valid_end_dt", length = 8)
    private String validEndDt;

    /** 처리자번호 */
    @Column(name = "processor_no", length = 20)
    private String processorNo;

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
    public PayAcct(String payAcctNo, String tenantId,
                   String billingAcctNo, String payMeansId,
                   String payMethodCd, String payAmtType,
                   BigDecimal fixedPayAmt, String prePartPayCd,
                   String unitCd, String payCycleCd, String payCycleDayCd,
                   String wdrawTypeDtlCd, Integer priority,
                   String validStartDt, String validEndDt,
                   String processorNo, String useYn, String reqReason,
                   String regUsrId, String updUsrId) {
        this.payAcctNo       = payAcctNo;
        this.tenantId        = tenantId;
        this.billingAcctNo   = billingAcctNo;
        this.payMeansId      = payMeansId;
        this.payMethodCd     = payMethodCd;
        this.payAmtType      = payAmtType;
        this.fixedPayAmt     = fixedPayAmt;
        this.prePartPayCd    = prePartPayCd;
        this.unitCd          = unitCd;
        this.payCycleCd      = payCycleCd;
        this.payCycleDayCd   = payCycleDayCd;
        this.wdrawTypeDtlCd  = wdrawTypeDtlCd;
        this.priority        = (priority != null) ? priority : 1;
        this.validStartDt    = validStartDt;
        this.validEndDt      = validEndDt;
        this.processorNo     = processorNo;
        this.useYn           = (useYn != null) ? useYn : "Y";
        this.reqReason       = reqReason;
        this.regUsrId        = regUsrId;
        this.updUsrId        = updUsrId;
    }

    // ── 비즈니스 메서드 ──────────────────────────────────────────────

    /** 지정금액 납부로 변경 */
    public void changeToFixedAmt(BigDecimal amount, String reason) {
        this.payAmtType  = "FIXED";
        this.fixedPayAmt = amount;
        this.reqReason   = reason;
    }

    /** 전액 납부로 변경 */
    public void changeToFullAmt(String reason) {
        this.payAmtType  = "FULL";
        this.fixedPayAmt = null;
        this.reqReason   = reason;
    }

    /** 납부수단 교체 */
    public void changePayMeans(String newPayMeansId, String newPayMethodCd, String reason) {
        this.payMeansId  = newPayMeansId;
        this.payMethodCd = newPayMethodCd;
        this.reqReason   = reason;
    }

    /** 납부계정 비활성화 */
    public void disable(String updUsrId, String reason) {
        this.useYn     = "N";
        this.reqReason = reason;
        this.updUsrId  = updUsrId;
    }
}
