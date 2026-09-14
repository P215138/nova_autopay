package com.skt.autopay.service.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.service.infra.persistence.entity.ServiceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 서비스 (SERVICE) 매퍼.
 */
@Mapper
public interface ServiceMapper extends BaseMapper<ServiceEntity> {
}
