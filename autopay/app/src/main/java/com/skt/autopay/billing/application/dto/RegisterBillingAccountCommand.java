package com.skt.autopay.billing.application.dto;

/**
 * 청구계정 등록 커맨드.
 *
 * @param tenantId         테넌트ID
 * @param customerNo       고객번호
 * @param payClCode        납부구분코드
 * @param billingCycleCode 청구주기코드
 */
public record RegisterBillingAccountCommand(
        String tenantId,
        String customerNo,
        String payClCode,
        String billingCycleCode
) {
}
