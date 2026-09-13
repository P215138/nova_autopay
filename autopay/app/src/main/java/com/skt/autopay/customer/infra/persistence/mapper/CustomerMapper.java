package com.skt.autopay.customer.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.customer.infra.persistence.entity.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 고객 (CUSTOMER) 매퍼
 */
@Mapper
public interface CustomerMapper extends BaseMapper<CustomerEntity> {
}
