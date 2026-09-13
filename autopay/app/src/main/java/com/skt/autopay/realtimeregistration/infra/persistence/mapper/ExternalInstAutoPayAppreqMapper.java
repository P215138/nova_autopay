package com.skt.autopay.realtimeregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.realtimeregistration.infra.persistence.entity.ExternalInstAutoPayAppreqEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 외부기관자동납부신청 (EXTERNAL_INST_AUTO_PAY_APPREQ) 매퍼
 */
@Mapper
public interface ExternalInstAutoPayAppreqMapper extends BaseMapper<ExternalInstAutoPayAppreqEntity> {
}
