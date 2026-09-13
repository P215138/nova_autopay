package com.skt.autopay.paymeansregistration.domain.model;

/**
 * 실시간인증 상태코드 (AUTHENTC_STAT_CD).
 *
 * <ul>
 *   <li>REQUESTED(01) 인증중 : 인증 요청 직후</li>
 *   <li>SUCCESS(10)   성공</li>
 *   <li>FAILED(40)    실패</li>
 * </ul>
 */
public enum RealtmAuthStatus {

    REQUESTED("01", "인증중"),
    SUCCESS("10", "성공"),
    FAILED("40", "실패");

    private final String code;
    private final String label;

    RealtmAuthStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }
}
