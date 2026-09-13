package com.skt.autopay.paymeansregistration.application.port;

/**
 * A3 대리납부 동의요청 발송 포트 (SMS/알림).
 *
 * <p>납부수단_구조설계 §5 기준. 비대면 대리납부 1차에서 인증 성공 후
 * 수단 소유주(대리납부자)에게 동의요청 링크를 발송한다.
 */
public interface SmsAuthGatewayPort {

    /**
     * 소유주에게 대리납부 동의요청 발송.
     *
     * @param command 발송 요청
     * @return 발송 결과
     */
    SendResult sendConsentRequest(SendCommand command);

    record SendCommand(
            String tenantId,
            Long agentPayMapSeqno,   // 대리납부맵 일련번호
            String ownerPhoneNo,     // 소유주 휴대폰번호
            String ownerCustomerNo   // 소유주 고객번호
    ) {
    }

    record SendResult(
            boolean success,
            String messageId,
            String resultMessage
    ) {
        public static SendResult success(String messageId) {
            return new SendResult(true, messageId, "발송 성공");
        }
    }
}
