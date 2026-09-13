package com.skt.autopay.realtimeregistration.application.dto;

/**
 * 납부계정 조회 뷰.
 */
public record PaymentAccountView(
        Long payAcctNo,
        String tenantId,
        String payCatgCd,
        Long invoiceAcctNo,
        String payMthdCd,
        String payCycleCd,
        String payCycleDayCd,
        Long payMeansNo,
        Long reservePayMeansNo,
        String payAcctStatCd
) {
}
