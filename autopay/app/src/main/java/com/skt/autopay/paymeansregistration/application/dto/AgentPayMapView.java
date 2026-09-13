package com.skt.autopay.paymeansregistration.application.dto;

import java.time.LocalDateTime;

/**
 * 대리납부 맵핑 조회 뷰 (U11).
 */
public record AgentPayMapView(
        Long agentPayMapSeqno,
        String agentPayerCustomerNo,
        String bnfcPayerCustomerNo,
        Long payMeansNo,
        String relCatgCd,
        String statusCd,
        String statusLabel,
        LocalDateTime validStartDtm,
        LocalDateTime validEndDtm
) {
}
