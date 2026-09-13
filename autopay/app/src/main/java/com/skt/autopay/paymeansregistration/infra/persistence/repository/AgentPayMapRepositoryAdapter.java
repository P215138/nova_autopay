package com.skt.autopay.paymeansregistration.infra.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.skt.autopay.paymeansregistration.application.port.AgentPayMapRepositoryPort;
import com.skt.autopay.paymeansregistration.domain.model.AgentPayMap;
import com.skt.autopay.paymeansregistration.domain.model.AgentPayMapStatus;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.AgentPayMapEntity;
import com.skt.autopay.paymeansregistration.infra.persistence.mapper.AgentPayMapMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 대리납부 맵핑 저장소 어댑터 - MyBatis-Plus Mapper 위임 + 도메인↔엔티티 변환.
 */
@Repository
public class AgentPayMapRepositoryAdapter implements AgentPayMapRepositoryPort {

    private final AgentPayMapMapper mapper;

    public AgentPayMapRepositoryAdapter(AgentPayMapMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public AgentPayMap save(AgentPayMap agentPayMap) {
        if (agentPayMap.getAgentPayMapSeqno() == null) {
            agentPayMap.setAgentPayMapSeqno(generateSeqno());
        }
        mapper.insert(toEntity(agentPayMap));
        return agentPayMap;
    }

    @Override
    public void update(AgentPayMap agentPayMap) {
        mapper.updateById(toEntity(agentPayMap));
    }

    @Override
    public Optional<AgentPayMap> findBySeqno(Long agentPayMapSeqno, String tenantId) {
        LambdaQueryWrapper<AgentPayMapEntity> q = new LambdaQueryWrapper<AgentPayMapEntity>()
                .eq(AgentPayMapEntity::getAgentPayMapSeqno, agentPayMapSeqno)
                .eq(AgentPayMapEntity::getTenantId, tenantId);
        return Optional.ofNullable(mapper.selectOne(q)).map(this::toDomain);
    }

    @Override
    public List<AgentPayMap> findByBeneficiary(String tenantId, String bnfcPayerCustomerNo) {
        LambdaQueryWrapper<AgentPayMapEntity> q = new LambdaQueryWrapper<AgentPayMapEntity>()
                .eq(AgentPayMapEntity::getTenantId, tenantId)
                .eq(AgentPayMapEntity::getBnfcPayerCustomerNo, bnfcPayerCustomerNo);
        return mapper.selectList(q).stream().map(this::toDomain).toList();
    }

    @Override
    public List<AgentPayMap> findByAgentPayer(String tenantId, String agentPayerCustomerNo) {
        LambdaQueryWrapper<AgentPayMapEntity> q = new LambdaQueryWrapper<AgentPayMapEntity>()
                .eq(AgentPayMapEntity::getTenantId, tenantId)
                .eq(AgentPayMapEntity::getAgentPayerCustomerNo, agentPayerCustomerNo);
        return mapper.selectList(q).stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsActiveRelation(String tenantId, String agentPayerCustomerNo,
                                        String bnfcPayerCustomerNo) {
        LambdaQueryWrapper<AgentPayMapEntity> q = new LambdaQueryWrapper<AgentPayMapEntity>()
                .eq(AgentPayMapEntity::getTenantId, tenantId)
                .eq(AgentPayMapEntity::getAgentPayerCustomerNo, agentPayerCustomerNo)
                .eq(AgentPayMapEntity::getBnfcPayerCustomerNo, bnfcPayerCustomerNo)
                // 진행중(요청 10) / 활성(20) 만 중복으로 판단, 거절30/중단40/종료50 제외
                .in(AgentPayMapEntity::getAgentPayMapStatCd,
                        AgentPayMapStatus.REQ.code(), AgentPayMapStatus.ACT.code());
        return mapper.selectCount(q) > 0;
    }

    private Long generateSeqno() {
        return System.currentTimeMillis() * 1000 + ThreadLocalRandom.current().nextInt(1000);
    }

    private AgentPayMapEntity toEntity(AgentPayMap m) {
        AgentPayMapEntity e = new AgentPayMapEntity();
        e.setAgentPayMapSeqno(m.getAgentPayMapSeqno());
        e.setTenantId(m.getTenantId());
        e.setAgentPayerCustomerNo(m.getAgentPayerCustomerNo());
        e.setBnfcPayerCustomerNo(m.getBnfcPayerCustomerNo());
        e.setPayMeansNo(m.getPayMeansNo());
        e.setRelCatgCd(m.getRelCatgCd());
        e.setAgentPayMapStatCd(m.getStatus() != null ? m.getStatus().code() : null);
        e.setValidStartDtm(m.getValidStartDtm());
        e.setValidEndDtm(m.getValidEndDtm());
        return e;
    }

    private AgentPayMap toDomain(AgentPayMapEntity e) {
        return AgentPayMap.reconstitute(
                e.getAgentPayMapSeqno(),
                e.getTenantId(),
                e.getAgentPayerCustomerNo(),
                e.getBnfcPayerCustomerNo(),
                e.getPayMeansNo(),
                e.getRelCatgCd(),
                e.getAgentPayMapStatCd() != null ? AgentPayMapStatus.fromCode(e.getAgentPayMapStatCd()) : null,
                e.getValidStartDtm(),
                e.getValidEndDtm());
    }
}
