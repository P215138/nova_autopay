package com.skt.autopay.clientrest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * 외부 금융기관 REST 호출용 RestClient 설정.
 *
 * <p>base-url 은 application.yml 의 autopay.external.finance.base-url 로 주입.
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient financeRestClient(
            @Value("${autopay.external.finance.base-url:http://localhost:9900}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
