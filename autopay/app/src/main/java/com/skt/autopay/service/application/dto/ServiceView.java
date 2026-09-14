package com.skt.autopay.service.application.dto;

/**
 * 서비스 조회 뷰.
 *
 * @param serviceMgmtNo       서비스관리번호
 * @param tenantId            테넌트ID
 * @param billingAccountNo    청구계정번호
 * @param serviceNoAltrnateId 서비스번호대체ID
 * @param serviceStatCd       서비스상태코드 (AC/TG/SP)
 * @param serviceStatLabel    서비스상태명 (사용중/해지/정지)
 */
public record ServiceView(
        String serviceMgmtNo,
        String tenantId,
        String billingAccountNo,
        String serviceNoAltrnateId,
        String serviceStatCd,
        String serviceStatLabel
) {
}
