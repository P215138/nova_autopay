package com.skt.autopay.paymeansregistration.application.dto;

/**
 * 납부수단 조회 뷰 (U3 지갑 목록/단건).
 */
public record PayMeansView(
        Long payMeansNo,
        String customerNo,
        String payMeansTypeCd,
        String payMeansTypeLabel,
        String payMeansStatCd,
        String payMeansStatLabel,
        String payMeansNm,
        String fincInstCd,
        String holderNm,
        Long bankacctCardAltrnateId,
        /** 계좌/카드번호 마스킹 표시값 (금고에서 복호화 후 마스킹). 원본은 노출하지 않음. */
        String accountOrCardNoMasked
) {
}
