package com.skt.autopay.boot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot main
 *
 * <p>모든 모듈(paymeansregistration, realtimeregistration 등)을 스캔하도록
 * base package를 com.skt.autopay 로 확장한다.
 */
@SpringBootApplication(scanBasePackages = "com.skt.autopay")
public class AutopayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutopayApplication.class, args);
    }
}
