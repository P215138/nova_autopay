package com.skt.autopay.paymeansregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.PayMeansEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 납부수단 (PAY_MEANS) 매퍼
 */
@Mapper
public interface PayMeansMapper extends BaseMapper<PayMeansEntity> {
}
