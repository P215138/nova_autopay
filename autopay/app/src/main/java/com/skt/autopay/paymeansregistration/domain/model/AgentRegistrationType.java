package com.skt.autopay.paymeansregistration.domain.model;

/**
 * 대리인 납부수단 등록 방식.
 *
 * <ul>
 *   <li>FACE_TO_FACE(대면) : 본인 등록과 동일하게 즉시 활성(10)</li>
 *   <li>NON_FACE(비대면)   : 인증 성공 시 등록중(01) + 소유주 동의 대기</li>
 * </ul>
 */
public enum AgentRegistrationType {
    FACE_TO_FACE,
    NON_FACE
}
