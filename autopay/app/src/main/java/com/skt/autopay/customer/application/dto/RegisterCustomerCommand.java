package com.skt.autopay.customer.application.dto;

/**
 * 고객 등록 커맨드.
 *
 * @param customerNo  고객번호 (미입력 시 서버 채번)
 * @param customerNm  이름
 * @param rrn         주민번호
 * @param genderCd    성별 (M 남 / F 여)
 * @param mobilePhno  이동전화번호
 */
public record RegisterCustomerCommand(
        String customerNo,
        String customerNm,
        String rrn,
        String genderCd,
        String mobilePhno
) {
}
