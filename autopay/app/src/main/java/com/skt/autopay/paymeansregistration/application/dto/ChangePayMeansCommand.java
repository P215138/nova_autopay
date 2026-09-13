package com.skt.autopay.paymeansregistration.application.dto;

/**
 * 납부수단 정보변경 커맨드 (U4 별칭/연락처).
 *
 * @param tenantId   테넌트ID
 * @param payMeansNo 납부수단번호
 * @param payMeansNm 변경할 별칭 (null 이면 미변경)
 */
public record ChangePayMeansCommand(
        String tenantId,
        Long payMeansNo,
        String payMeansNm
) {
}
