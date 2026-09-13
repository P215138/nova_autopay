package com.skt.autopay.realtimeregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.realtimeregistration.infra.persistence.entity.PayAcctAuthentcEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 납부계정인증 (PAY_ACCT_AUTHENTC) 매퍼
 */
@Mapper
public interface PayAcctAuthentcMapper extends BaseMapper<PayAcctAuthentcEntity> {
}
