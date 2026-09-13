package com.nova.domain.billing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

/**
 * 자동납부인출일정 복합키
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AutoPaymentScheduleId implements Serializable {

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId;

    @Column(name = "withdrawal_year_month", length = 6, nullable = false)
    private String withdrawalYearMonth; // 인출년월 (YYYYMM)

    @Column(name = "payment_account_no", length = 20, nullable = false)
    private String paymentAccountNo; // 납부계정번호
}
