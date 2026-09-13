package com.skt.autopay.realtimeregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.realtimeregistration.infra.persistence.entity.PayAcctEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 납부계정 (PAY_ACCT) 매퍼
 */
@Mapper
public interface PayAcctMapper extends BaseMapper<PayAcctEntity> {
}
