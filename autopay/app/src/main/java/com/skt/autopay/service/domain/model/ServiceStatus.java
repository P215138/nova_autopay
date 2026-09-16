package com.skt.autopay.service.domain.model;

/**
 * 서비스 상태코드 (SERVICE_STAT_CD)
 *
 * <ul>
 *   <li>AC 사용중</li>
 *   <li>SP 정지</li>
 *   <li>TG 해지</li>
 * </ul>
 */
public enum ServiceStatus {

    ACTIVE("AC", "사용중"),
    SUSPENDED("SP", "정지"),
    TERMINATED("TG", "해지");

    private final String code;
    private final String label;

    ServiceStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static ServiceStatus fromCode(String code) {
        for (ServiceStatus s : values()) {
            if (s.code.equals(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown ServiceStatus code: " + code);
    }
}
