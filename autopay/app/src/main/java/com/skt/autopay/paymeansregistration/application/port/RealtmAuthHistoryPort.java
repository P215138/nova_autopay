package com.skt.autopay.paymeansregistration.application.port;

/**
 * 납부수단 실시간인증 이력 포트 (PAY_MEANS_REALTM_AUTHENTC).
 *
 * <p>흐름: 인증 요청 시 이력 생성(인증중) → 인증 결과 반영(성공/실패) →
 * 성공 시 채번된 납부수단번호를 이력에 반영.
 */
public interface RealtmAuthHistoryPort {

    /**
     * 인증 요청 이력 생성 (상태 = 인증중).
     *
     * @return 채번된 실시간인증순번(REALTM_AUTHENTC_SEQ)
     */
    Long createRequested(RequestCommand command);

    /** 인증 결과 반영 (성공/실패). 실패해도 이력은 유지된다. */
    void applyResult(Long realtmAuthentcSeq, String tenantId,
                     boolean success, String externalAuthKey,
                     String resultCode, String resultMessage);

    /** 인증 성공 후 생성된 납부수단번호를 이력에 반영. */
    void linkPayMeans(Long realtmAuthentcSeq, String tenantId, Long payMeansNo);

    /** 인증 요청 값 */
    record RequestCommand(
            String tenantId,
            String payMeansTypeCd,   // 납부방법/유형 코드
            String authentcMthdCd,   // 인증방법코드
            String fincInstCd,       // 납부기관코드
            String accountOrCardNo   // 계좌/카드번호 (은행계좌코드 등으로 기록)
    ) {
    }
}
