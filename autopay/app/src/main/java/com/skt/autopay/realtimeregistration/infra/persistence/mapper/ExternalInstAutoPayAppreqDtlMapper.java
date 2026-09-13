package com.skt.autopay.realtimeregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.realtimeregistration.infra.persistence.entity.ExternalInstAutoPayAppreqDtlEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 외부기관자동납부신청상세 (EXTERNAL_INST_AUTO_PAY_APPREQ_DTL) 매퍼
 */
@Mapper
public interface ExternalInstAutoPayAppreqDtlMapper extends BaseMapper<ExternalInstAutoPayAppreqDtlEntity> {
}
