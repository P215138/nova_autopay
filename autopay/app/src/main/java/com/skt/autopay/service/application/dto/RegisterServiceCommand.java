package com.skt.autopay.service.application.dto;

/**
 * 서비스 등록 커맨드.
 *
 * @param tenantId            테넌트ID
 * @param billingAccountNo    청구계정번호 (BILLING_ACCOUNT 연동)
 * @param serviceNoAltrnateId 서비스번호대체ID (전화번호 대체ID)
 */
public record RegisterServiceCommand(
        String tenantId,
        String billingAccountNo,
        String serviceNoAltrnateId
) {
}
