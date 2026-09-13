package com.skt.autopay.paymeansregistration.application.dto;

/**
 * 납부수단 등록/조회 결과.
 *
 * @param payMeansNo        납부수단번호
 * @param payMeansStatCd    납부수단상태코드
 * @param payMeansStatLabel 상태 라벨
 * @param agentPayMapSeqno  대리납부맵 일련번호 (대리납부 등록 시)
 */
public record PayMeansResult(
        Long payMeansNo,
        String payMeansStatCd,
        String payMeansStatLabel,
        Long agentPayMapSeqno
) {
}
