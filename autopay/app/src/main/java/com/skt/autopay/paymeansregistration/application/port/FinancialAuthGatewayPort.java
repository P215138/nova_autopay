package com.skt.autopay.paymeansregistration.application.port;

/**
 * 금융기관 단순 실시간인증 게이트웨이 포트 (A1 계좌실명조회 / A2 카드유효성).
 *
 * <p>납부수단_구조설계 §5 기준. 실제 구현은 extauth 소유이나, 여기서는
 * 스텁 어댑터로 대체한다. auth-first 원칙에 따라 등록 전에 먼저 호출된다.
 */
public interface FinancialAuthGatewayPort {

    /**
     * 계좌/카드 유효성 실시간 인증.
     *
     * @param command 인증 요청
     * @return 인증 결과 (성공/실패 + 외부인증키)
     */
    AuthResult authenticate(AuthCommand command);

    /** 인증 요청 값 객체 */
    record AuthCommand(
            String tenantId,
            String payMeansTypeCd,   // 01 은행 / 02 카드 / 05 간편결제
            String fincInstCd,       // 금융기관/카드사 코드
            String accountOrCardNo,  // 계좌번호 또는 카드번호 (평문, 저장 안 함)
            String holderNm,         // 예금주/카드주 명
            String cardValidYymm     // 카드 유효기간 (카드인 경우)
    ) {
    }

    /** 인증 결과 값 객체 */
    record AuthResult(
            boolean success,
            String externalAuthKey,  // 외부 인증키
            String resultCode,
            String resultMessage
    ) {
        public static AuthResult success(String externalAuthKey) {
            return new AuthResult(true, externalAuthKey, "0000", "인증 성공");
        }

        public static AuthResult failure(String resultCode, String resultMessage) {
            return new AuthResult(false, null, resultCode, resultMessage);
        }
    }
}
