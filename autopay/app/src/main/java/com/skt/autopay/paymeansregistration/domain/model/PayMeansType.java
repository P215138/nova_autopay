package com.skt.autopay.paymeansregistration.domain.model;

/**
 * 납부수단 유형코드 (PAY_MEANS_TYPE_CD)
 *
 * <p>은행(01) / 카드(02) / 간편결제(05). 간편결제는 대리납부 불가.
 */
public enum PayMeansType {

    BANK("01", "은행"),
    CARD("02", "카드"),
    SIMPLE_PAY("05", "간편결제");

    private final String code;
    private final String label;

    PayMeansType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    /** 대리납부 가능 여부 (간편결제는 불가) */
    public boolean isAgentPayable() {
        return this != SIMPLE_PAY;
    }

    public static PayMeansType fromCode(String code) {
        for (PayMeansType t : values()) {
            if (t.code.equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Unknown PayMeansType code: " + code);
    }
}
