package com.skt.autopay.realtimeregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.realtimeregistration.infra.persistence.entity.PayAcctRcptnEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 납부계정접수 (PAY_ACCT_RCPTN) 매퍼
 */
@Mapper
public interface PayAcctRcptnMapper extends BaseMapper<PayAcctRcptnEntity> {
}
