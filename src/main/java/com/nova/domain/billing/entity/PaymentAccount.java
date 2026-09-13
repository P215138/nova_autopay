package com.nova.domain.billing.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 납부계정
 * - 청구계정 1개에 N개 매핑 가능 (TOBE 핵심 엔티티)
 * - 납부방법코드: 은행 / 카드 / 간편결제
 */
@Entity
@Table(name = "payment_account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentAccount {

    @Id
    @Column(name = "payment_account_no", length = 20, nullable = false)
    private String paymentAccountNo; // #납부계정번호 (PK)

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId; // 테넌트ID

    @Column(name = "billing_account_no", length = 20, nullable = false)
    private String billingAccountNo; // 청구계정번호 (FK, 1:N)

    @Column(name = "payment_method_code", length = 10, nullable = false)
    private String paymentMethodCode; // 납부방법코드 (은행/카드/간편결제)

    @Column(name = "unit_code", length = 5)
    private String unitCode; // 단분코드

    @Column(name = "payment_amount_type", length = 5)
    private String paymentAmountType; // 납부금액유형

    @Column(name = "pre_payment_collect_type", length = 5)
    private String prePaymentCollectType; // 선분류부수납구분코드

    @Column(name = "processor_no", length = 20)
    private String processorNo; // 처리자번호

    @Column(name = "request_reason", length = 200)
    private String requestReason; // 요청사유

    @Column(name = "use_yn", length = 1, nullable = false)
    private String useYn = "Y";

    @Builder
    public PaymentAccount(String paymentAccountNo, String tenantId,
                          String billingAccountNo, String paymentMethodCode,
                          String unitCode, String paymentAmountType,
                          String prePaymentCollectType, String processorNo,
                          String requestReason) {
        this.paymentAccountNo = paymentAccountNo;
        this.tenantId = tenantId;
        this.billingAccountNo = billingAccountNo;
        this.paymentMethodCode = paymentMethodCode;
        this.unitCode = unitCode;
        this.paymentAmountType = paymentAmountType;
        this.prePaymentCollectType = prePaymentCollectType;
        this.processorNo = processorNo;
        this.requestReason = requestReason;
    }
}
