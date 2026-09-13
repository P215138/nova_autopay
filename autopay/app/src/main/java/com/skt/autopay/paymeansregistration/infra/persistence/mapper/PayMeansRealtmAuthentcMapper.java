package com.skt.autopay.paymeansregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.PayMeansRealtmAuthentcEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 납부수단실시간인증 (PAY_MEANS_REALTM_AUTHENTC) 매퍼
 */
@Mapper
public interface PayMeansRealtmAuthentcMapper extends BaseMapper<PayMeansRealtmAuthentcEntity> {
}
