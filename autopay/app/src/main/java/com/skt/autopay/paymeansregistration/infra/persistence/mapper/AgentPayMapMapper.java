package com.skt.autopay.paymeansregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.AgentPayMapEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 대리납부 맵핑 (PAY_AGENT_MAP) 매퍼
 */
@Mapper
public interface AgentPayMapMapper extends BaseMapper<AgentPayMapEntity> {
}
