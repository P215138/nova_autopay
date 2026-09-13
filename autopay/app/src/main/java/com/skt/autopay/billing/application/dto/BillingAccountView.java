package com.skt.autopay.billing.application.dto;

/**
 * 청구계정 조회 뷰.
 */
public record BillingAccountView(
        String billingAccountNo,
        String tenantId,
        String customerNo,
        String payClCode,
        String billingCycleCode,
        String useYn
) {
}
