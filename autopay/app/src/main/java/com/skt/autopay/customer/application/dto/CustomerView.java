package com.skt.autopay.customer.application.dto;

/**
 * 고객 조회 뷰.
 */
public record CustomerView(
        String customerNo,
        String customerNm,
        String rrn,
        String genderCd,
        String mobilePhno
) {
}
