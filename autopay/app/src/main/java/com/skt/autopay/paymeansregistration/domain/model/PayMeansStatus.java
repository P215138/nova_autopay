package com.skt.autopay.paymeansregistration.domain.model;

/**
 * 납부수단 상태코드 (MEANS_STAT_CD)
 *
 * <p>납부수단_구조설계 §3 기준.
 * <ul>
 *   <li>REGISTERING(01) 등록중 : 대리납부(비대면) 소유주 동의 대기</li>
 *   <li>AUTH_WAITING(02) 인증대기 : 금융기관 장애·비동기 인증 대기</li>
 *   <li>ACTIVE(10)       활성   : 실시간인증 완료, 사용 가능</li>
 *   <li>SUSPENDED(20)    정지   : 신규 매핑 차단(기존 유지)</li>
 *   <li>TERMINATED(30)   해지   : 논리 삭제</li>
 *   <li>CLOSED(40)       종료   : 대리납부 동의 거부/지연, 인증대기 최종 실패</li>
 * </ul>
 */
public enum PayMeansStatus {

    REGISTERING("01", "등록중"),
    AUTH_WAITING("02", "인증대기"),
    ACTIVE("10", "활성"),
    SUSPENDED("20", "정지"),
    TERMINATED("30", "해지"),
    CLOSED("40", "종료");

    private final String code;
    private final String label;

    PayMeansStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static PayMeansStatus fromCode(String code) {
        for (PayMeansStatus s : values()) {
            if (s.code.equals(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown PayMeansStatus code: " + code);
    }
}
