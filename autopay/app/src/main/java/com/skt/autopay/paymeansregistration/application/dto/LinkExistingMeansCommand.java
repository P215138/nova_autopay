package com.skt.autopay.paymeansregistration.application.dto;

/**
 * 기존 납부수단 재사용(대리납부) 커맨드.
 *
 * <p>부모(소유주)가 이미 등록한 납부수단을 자식(피대리납부자)이 사용하도록 연결.
 * 새 PayMeans를 만들지 않고, 금고에서 원본을 복호화해 재인증한 뒤 AgentPayMap만 추가한다.
 *
 * @param tenantId              테넌트ID
 * @param payMeansNo            재사용할 기존 납부수단번호 (소유주=부모 소유)
 * @param agentPayerCustomerNo  대리납부자(소유주=부모) 고객번호
 * @param bnfcPayerCustomerNo   피대리납부자(사용자=자식) 고객번호
 * @param relCatgCd             관계구분코드
 * @param ownerPhoneNo          소유주 휴대폰번호 (동의요청 발송용)
 */
public record LinkExistingMeansCommand(
        String tenantId,
        Long payMeansNo,
        String agentPayerCustomerNo,
        String bnfcPayerCustomerNo,
        String relCatgCd,
        String ownerPhoneNo
) {
}
