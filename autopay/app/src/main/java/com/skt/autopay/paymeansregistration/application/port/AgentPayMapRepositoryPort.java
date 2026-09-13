package com.skt.autopay.paymeansregistration.application.port;

import com.skt.autopay.paymeansregistration.domain.model.AgentPayMap;

import java.util.List;
import java.util.Optional;

/**
 * 대리납부 맵핑 저장소 포트.
 */
public interface AgentPayMapRepositoryPort {

    /** 신규 저장 후 채번된 일련번호를 도메인에 반영하여 반환 */
    AgentPayMap save(AgentPayMap agentPayMap);

    void update(AgentPayMap agentPayMap);

    Optional<AgentPayMap> findBySeqno(Long agentPayMapSeqno, String tenantId);

    /** 피대리납부자(수단 사용자) 기준 목록 */
    List<AgentPayMap> findByBeneficiary(String tenantId, String bnfcPayerCustomerNo);

    /** 대리납부자(수단 소유주) 기준 목록 */
    List<AgentPayMap> findByAgentPayer(String tenantId, String agentPayerCustomerNo);

    /**
     * 동일 대리납부자↔피대리납부자 관계가 진행중(요청)/활성 상태로 이미 존재하는지 확인.
     * 거절/중단/종료 건은 제외(재등록 허용).
     */
    boolean existsActiveRelation(String tenantId, String agentPayerCustomerNo, String bnfcPayerCustomerNo);
}
