package com.skt.autopay.paymeansregistration.application.command;

import com.skt.autopay.paymeansregistration.application.dto.AgentPayApprovalCommand;
import com.skt.autopay.paymeansregistration.application.dto.PayMeansResult;
import com.skt.autopay.paymeansregistration.application.port.AgentPayMapRepositoryPort;
import com.skt.autopay.paymeansregistration.application.port.PayMeansRepositoryPort;
import com.skt.autopay.paymeansregistration.domain.model.AgentPayMap;
import com.skt.autopay.paymeansregistration.domain.model.PayMeans;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 대리납부 비대면 2차 승인/거절 처리 서비스 (U10).
 *
 * <p>소유주 동의 완료 시 PayMeans 등록중(01) → 활성(10), AgentPayMap 요청 → 활성.
 * 거절/지연 시 PayMeans 등록중(01) → 종료(40), AgentPayMap 요청 → 거절.
 */
@Service
public class AgentPayRequestService {

    private final PayMeansRepositoryPort payMeansRepository;
    private final AgentPayMapRepositoryPort agentPayMapRepository;

    public AgentPayRequestService(PayMeansRepositoryPort payMeansRepository,
                                  AgentPayMapRepositoryPort agentPayMapRepository) {
        this.payMeansRepository = payMeansRepository;
        this.agentPayMapRepository = agentPayMapRepository;
    }

    @Transactional
    public PayMeansResult completeConsent(AgentPayApprovalCommand cmd) {
        AgentPayMap map = agentPayMapRepository
                .findBySeqno(cmd.agentPayMapSeqno(), cmd.tenantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "대리납부 맵핑을 찾을 수 없습니다: " + cmd.agentPayMapSeqno()));

        PayMeans payMeans = payMeansRepository
                .findByNo(map.getPayMeansNo(), cmd.tenantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "납부수단을 찾을 수 없습니다: " + map.getPayMeansNo()));

        // 기존 수단 재사용(linkExistingMeansToAgent)으로 만든 관계는 PayMeans가 이미 활성(10)이다.
        // 이 경우 PayMeans 상태 전이는 생략하고 AgentPayMap만 확정한다.
        // 신규 비대면 등록(등록중 01)인 경우에만 PayMeans 상태를 전이한다.
        boolean payMeansPending = payMeans.isNonFacePending();

        if (cmd.approved()) {
            if (payMeansPending) {
                payMeans.approveByOwner();   // 01 -> 10
            }
            map.approve();                   // 요청 -> 활성
        } else {
            if (payMeansPending) {
                payMeans.closeByOwnerReject(); // 01 -> 40
            }
            map.reject();                      // 요청 -> 거절
        }

        // PayMeans 상태가 바뀐 경우에만 update (기존 활성 수단은 건드리지 않음)
        if (payMeansPending) {
            payMeansRepository.update(payMeans);
        }
        agentPayMapRepository.update(map);

        return new PayMeansResult(
                payMeans.getPayMeansNo(),
                payMeans.getStatus().code(),
                payMeans.getStatus().label(),
                map.getAgentPayMapSeqno());
    }

    /**
     * 대리납부 관계 해지. AgentPayMap 종료(TRM) + soft delete.
     * PayMeans(소유주 수단) 자체는 건드리지 않는다(다른 관계·본인 사용에 영향 없음).
     */
    @Transactional
    public void terminateRelation(Long agentPayMapSeqno, String tenantId) {
        AgentPayMap map = agentPayMapRepository
                .findBySeqno(agentPayMapSeqno, tenantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "대리납부 맵핑을 찾을 수 없습니다: " + agentPayMapSeqno));
        map.terminate();
        agentPayMapRepository.update(map);
    }
}
