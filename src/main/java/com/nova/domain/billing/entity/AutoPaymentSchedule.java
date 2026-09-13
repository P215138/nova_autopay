package com.nova.domain.billing.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 자동납부인출일정
 * - 납부계정별 자동 인출 일정 관리
 * - 인출유형: Daily / EDI / CMS / 간편결제
 * - 납부주기일코드(2자리): 15일, 21일, 23일, 25일, 말일
 */
@Entity
@Table(name = "auto_payment_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AutoPaymentSchedule {

    @EmbeddedId
    private AutoPaymentScheduleId id; // 복합키

    @Column(name = "payment_method_code", length = 10, nullable = false)
    private String paymentMethodCode; // 납부방법코드

    @Column(name = "payment_cycle_code", length = 5)
    private String paymentCycleCode; // 납부주기코드

    @Column(name = "payment_cycle_day_code", length = 2)
    private String paymentCycleDayCode; // 납부주기일코드 (2자리: 15,21,23,25,말일)

    @Column(name = "withdrawal_type_code", length = 10)
    private String withdrawalTypeCode; // 인출유형코드 (Daily/EDI/CMS/간편결제)

    @Column(name = "request_reason", length = 200)
    private String requestReason; // 요청사유

    @Builder
    public AutoPaymentSchedule(AutoPaymentScheduleId id, String paymentMethodCode,
                                String paymentCycleCode, String paymentCycleDayCode,
                                String withdrawalTypeCode, String requestReason) {
        this.id = id;
        this.paymentMethodCode = paymentMethodCode;
        this.paymentCycleCode = paymentCycleCode;
        this.paymentCycleDayCode = paymentCycleDayCode;
        this.withdrawalTypeCode = withdrawalTypeCode;
        this.requestReason = requestReason;
    }
}
