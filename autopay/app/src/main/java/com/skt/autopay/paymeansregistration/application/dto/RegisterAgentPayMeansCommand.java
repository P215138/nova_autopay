package com.skt.autopay.paymeansregistration.application.dto;

/**
 * 대리인 납부수단 등록 커맨드 (U9).
 *
 * @param tenantId              테넌트ID
 * @param agentPayerCustomerNo  대리납부자(수단 소유주) 고객번호
 * @param bnfcPayerCustomerNo   피대리납부자(수단 사용자) 고객번호
 * @param payMeansTypeCd        납부수단유형코드 (01 은행 / 02 카드) - 간편결제(05) 불가
 * @param payMeansNm            납부수단 별칭
 * @param fincInstCd            금융기관/카드사 코드
 * @param accountOrCardNo       계좌/카드번호 (인증용)
 * @param holderNm              예금주/카드주 명 (= 소유주)
 * @param cardValidYymm         카드 유효기간
 * @param relCatgCd             관계구분코드 (부모/배우자/자녀)
 * @param ownerPhoneNo          소유주 휴대폰번호 (동의요청 발송용)
 * @param faceToFace            대면 여부 (true=대면 즉시활성, false=비대면 동의대기)
 */
public record RegisterAgentPayMeansCommand(
        String tenantId,
        String agentPayerCustomerNo,
        String bnfcPayerCustomerNo,
        String payMeansTypeCd,
        String payMeansNm,
        String fincInstCd,
        String accountOrCardNo,
        String holderNm,
        String cardValidYymm,
        String relCatgCd,
        String ownerPhoneNo,
        boolean faceToFace
) {
}
