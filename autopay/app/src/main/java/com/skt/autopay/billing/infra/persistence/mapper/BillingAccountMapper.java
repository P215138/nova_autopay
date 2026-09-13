package com.skt.autopay.billing.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.billing.infra.persistence.entity.BillingAccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 청구계정 (BILLING_ACCOUNT) 매퍼
 */
@Mapper
public interface BillingAccountMapper extends BaseMapper<BillingAccountEntity> {
}
