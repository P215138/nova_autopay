package com.skt.autopay.paymeansregistration.infra.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.BankacctCardInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 계좌카드정보 금고 (BANKACCT_CARD_INFO) 매퍼.
 */
@Mapper
public interface BankacctCardInfoMapper extends BaseMapper<BankacctCardInfoEntity> {
}
