package com.skt.autopay.paymeansregistration.application.dto;

/**
 * 납부수단 등록 커맨드 (본인/대리인 공통).
 *
 * @param tenantId          테넌트ID
 * @param customerNo        수단 소유주(명의자) 고객번호
 * @param payMeansTypeCd    납부수단유형코드 (01 은행 / 02 카드 / 05 간편결제)
 * @param payMeansNm        납부수단 별칭
 * @param fincInstCd        금융기관/카드사 코드
 * @param accountOrCardNo   계좌번호 또는 카드번호 (인증용, 저장 안 함)
 * @param holderNm          예금주/카드주 명
 * @param cardValidYymm     카드 유효기간 (카드인 경우)
 */
public record RegisterPayMeansCommand(
        String tenantId,
        String customerNo,
        String payMeansTypeCd,
        String payMeansNm,
        String fincInstCd,
        String accountOrCardNo,
        String holderNm,
        String cardValidYymm
) {
}
