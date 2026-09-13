package com.skt.autopay.realtimeregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.realtimeregistration.infra.persistence.entity.PayAcctAuthentcWttEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 납부계정인증(WTT) (PAY_ACCT_AUTHENTC_WTT) 매퍼
 */
@Mapper
public interface PayAcctAuthentcWttMapper extends BaseMapper<PayAcctAuthentcWttEntity> {
}
