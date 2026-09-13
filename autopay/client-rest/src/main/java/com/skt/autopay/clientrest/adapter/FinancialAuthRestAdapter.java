package com.skt.autopay.clientrest.adapter;

import com.skt.autopay.paymeansregistration.application.port.FinancialAuthGatewayPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * A1 계좌실명조회 / A2 카드유효성 - 실제 REST 연동 어댑터.
 *
 * <p>{@code rest-live} 프로파일에서만 활성화된다. 미지정 시 app 의
 * {@code FinancialAuthStubAdapter}(스텁)가 사용된다.
 *
 * <p>외부 금융기관 인증 API를 REST 로 호출하여 결과를 도메인 포트 규격으로 변환한다.
 */
@Component
@Profile("rest-live")
public class FinancialAuthRestAdapter implements FinancialAuthGatewayPort {

    private static final Logger log = LoggerFactory.getLogger(FinancialAuthRestAdapter.class);

    private final RestClient financeRestClient;

    public FinancialAuthRestAdapter(RestClient financeRestClient) {
        this.financeRestClient = financeRestClient;
    }

    @Override
    public AuthResult authenticate(AuthCommand command) {
        String path = resolvePath(command.payMeansTypeCd());
        try {
            ExternalAuthResponse response = financeRestClient.post()
                    .uri(path)
                    .body(new ExternalAuthRequest(
                            command.tenantId(),
                            command.fincInstCd(),
                            command.accountOrCardNo(),
                            command.holderNm(),
                            command.cardValidYymm()))
                    .retrieve()
                    .body(ExternalAuthResponse.class);

            if (response == null) {
                return AuthResult.failure("9999", "외부 인증 응답 없음");
            }
            if (response.success()) {
                return AuthResult.success(response.externalAuthKey());
            }
            return AuthResult.failure(response.resultCode(), response.resultMessage());

        } catch (RestClientException e) {
            log.error("금융기관 REST 인증 호출 실패 - path={}, msg={}", path, e.getMessage());
            return AuthResult.failure("9998", "외부 인증 통신 오류: " + e.getMessage());
        }
    }

    /** 납부수단유형별 외부 인증 엔드포인트 결정 (01 은행=계좌실명, 02 카드=유효성) */
    private String resolvePath(String payMeansTypeCd) {
        return switch (payMeansTypeCd) {
            case "01" -> "/ext/finance/account/verify";  // A1 계좌실명조회(KS-NET)
            case "02" -> "/ext/finance/card/verify";     // A2 카드유효성(카드사)
            default -> throw new IllegalArgumentException(
                    "REST 인증 미지원 납부수단유형: " + payMeansTypeCd);
        };
    }

    /** 외부 인증 요청 바디 */
    record ExternalAuthRequest(
            String tenantId,
            String fincInstCd,
            String accountOrCardNo,
            String holderNm,
            String cardValidYymm
    ) {
    }

    /** 외부 인증 응답 바디 */
    record ExternalAuthResponse(
            boolean success,
            String externalAuthKey,
            String resultCode,
            String resultMessage
    ) {
    }
}
