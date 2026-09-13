package com.skt.autopay.paymeansregistration.infra.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.skt.autopay.paymeansregistration.application.port.PayMeansRepositoryPort;
import com.skt.autopay.paymeansregistration.domain.model.PayMeans;
import com.skt.autopay.paymeansregistration.domain.model.PayMeansStatus;
import com.skt.autopay.paymeansregistration.domain.model.PayMeansType;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.PayMeansEntity;
import com.skt.autopay.paymeansregistration.infra.persistence.mapper.PayMeansMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 납부수단 저장소 어댑터 - MyBatis-Plus Mapper 위임 + 도메인↔엔티티 변환.
 */
@Repository
public class PayMeansRepositoryAdapter implements PayMeansRepositoryPort {

    private final PayMeansMapper mapper;

    public PayMeansRepositoryAdapter(PayMeansMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PayMeans save(PayMeans payMeans) {
        if (payMeans.getPayMeansNo() == null) {
            payMeans.setPayMeansNo(generateNo());
        }
        PayMeansEntity entity = toEntity(payMeans);
        mapper.insert(entity);
        return payMeans;
    }

    @Override
    public void update(PayMeans payMeans) {
        PayMeansEntity entity = toEntity(payMeans);
        mapper.updateById(entity);
    }

    @Override
    public Optional<PayMeans> findByNo(Long payMeansNo, String tenantId) {
        LambdaQueryWrapper<PayMeansEntity> q = new LambdaQueryWrapper<PayMeansEntity>()
                .eq(PayMeansEntity::getPayMeansNo, payMeansNo)
                .eq(PayMeansEntity::getTenantId, tenantId);
        return Optional.ofNullable(mapper.selectOne(q)).map(this::toDomain);
    }

    @Override
    public Optional<PayMeans> findById(Long payMeansNo) {
        return Optional.ofNullable(mapper.selectById(payMeansNo)).map(this::toDomain);
    }

    @Override
    public List<PayMeans> findByCustomer(String tenantId, String customerNo) {
        LambdaQueryWrapper<PayMeansEntity> q = new LambdaQueryWrapper<PayMeansEntity>()
                .eq(PayMeansEntity::getTenantId, tenantId)
                .eq(PayMeansEntity::getCustomerNo, customerNo);
        return mapper.selectList(q).stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsActiveByAltrnateId(String tenantId, Long bankacctCardAltrnateId) {
        if (bankacctCardAltrnateId == null) {
            return false;
        }
        LambdaQueryWrapper<PayMeansEntity> q = new LambdaQueryWrapper<PayMeansEntity>()
                .eq(PayMeansEntity::getTenantId, tenantId)
                .eq(PayMeansEntity::getBankacctCardAltrnateId, bankacctCardAltrnateId)
                // 유효 상태: 01 등록중 / 02 인증대기 / 10 활성
                .in(PayMeansEntity::getPayMeansStatCd, "01", "02", "10");
        return mapper.selectCount(q) > 0;
    }

    // 데모용 채번 (실제로는 시퀀스/채번 테이블 사용)
    private Long generateNo() {
        return System.currentTimeMillis() * 1000 + ThreadLocalRandom.current().nextInt(1000);
    }

    private PayMeansEntity toEntity(PayMeans m) {
        PayMeansEntity e = new PayMeansEntity();
        e.setPayMeansNo(m.getPayMeansNo());
        e.setTenantId(m.getTenantId());
        e.setCustomerNo(m.getCustomerNo());
        e.setPayMeansTypeCd(m.getType() != null ? m.getType().code() : null);
        e.setPayMeansStatCd(m.getStatus() != null ? m.getStatus().code() : null);
        e.setPayMeansChgReasonCd(m.getChangeReasonCd());
        e.setPayMeansNmEncrypt(m.getPayMeansNm());
        e.setFincInstCd(m.getFincInstCd());
        e.setIssuncCardcoCd(m.getIssuncCardcoCd());
        e.setBankacctCardAltrnateId(m.getBankacctCardAltrnateId());
        e.setCardValidYymm(m.getCardValidYymm());
        e.setBankacctCardhdrNmEncrypt(m.getHolderNm());
        return e;
    }

    private PayMeans toDomain(PayMeansEntity e) {
        return PayMeans.reconstitute(
                e.getPayMeansNo(),
                e.getTenantId(),
                e.getCustomerNo(),
                e.getPayMeansTypeCd() != null ? PayMeansType.fromCode(e.getPayMeansTypeCd()) : null,
                e.getPayMeansStatCd() != null ? PayMeansStatus.fromCode(e.getPayMeansStatCd()) : null,
                e.getPayMeansNmEncrypt(),
                e.getFincInstCd(),
                e.getBankacctCardAltrnateId(),
                e.getBankacctCardhdrNmEncrypt(),
                e.getPayMeansChgReasonCd());
    }
}
