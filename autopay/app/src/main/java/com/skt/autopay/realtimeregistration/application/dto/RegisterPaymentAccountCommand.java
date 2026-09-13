package com.skt.autopay.realtimeregistration.application.dto;

/**
 * 납부계정 등록 커맨드.
 *
 * @param tenantId          테넌트ID
 * @param invoiceAcctNo     청구계정번호 (후불)
 * @param payCatgCd         납부구분코드
 * @param payMthdCd         납부방법코드 (01 은행 / 02 카드 / 05 간편결제)
 * @param payCycleCd        납부주기코드
 * @param payCycleDayCd     납부주기일코드
 * @param payMeansNo        메인 납부수단번호
 * @param reservePayMeansNo 예비 납부수단번호 (없으면 null)
 */
public record RegisterPaymentAccountCommand(
        String tenantId,
        Long invoiceAcctNo,
        String payCatgCd,
        String payMthdCd,
        String payCycleCd,
        String payCycleDayCd,
        Long payMeansNo,
        Long reservePayMeansNo
) {
}
