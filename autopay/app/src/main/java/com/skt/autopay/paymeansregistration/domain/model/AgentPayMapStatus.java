package com.skt.autopay.paymeansregistration.domain.model;

/**
 * 대리납부 맵핑 상태코드 (AGENT_PAY_MAP_STAT_CD)
 *
 * <p>납부수단_구조설계 §2.3 기준.
 * <ul>
 *   <li>REQ  요청 : 등록 요청됨(소유주 동의 대기) - U9</li>
 *   <li>ACT  활성 : 소유주 동의 완료 - U10 성공</li>
 *   <li>REJ  거절 : 소유주 거절 - U10 거절</li>
 *   <li>STOP 중단 : 미이용 방치 (배치) </li>
 *   <li>TRM  종료 : 대리납/피대리납 요청 시 - U12</li>
 * </ul>
 */
public enum AgentPayMapStatus {

    REQ("10", "요청"),
    ACT("20", "활성"),
    REJ("30", "거절"),
    STOP("40", "중단"),
    TRM("50", "종료");

    private final String code;
    private final String label;

    AgentPayMapStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static AgentPayMapStatus fromCode(String code) {
        for (AgentPayMapStatus s : values()) {
            if (s.code.equals(code)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown AgentPayMapStatus code: " + code);
    }
}
