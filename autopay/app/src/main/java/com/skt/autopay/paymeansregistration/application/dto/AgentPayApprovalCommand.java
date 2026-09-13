package com.skt.autopay.paymeansregistration.application.dto;

/**
 * 대리납부 비대면 2차 - 소유주 승인/거절 처리 커맨드 (U10).
 *
 * @param tenantId          테넌트ID
 * @param agentPayMapSeqno  대리납부맵 일련번호
 * @param approved          true=승인, false=거절
 */
public record AgentPayApprovalCommand(
        String tenantId,
        Long agentPayMapSeqno,
        boolean approved
) {
}
