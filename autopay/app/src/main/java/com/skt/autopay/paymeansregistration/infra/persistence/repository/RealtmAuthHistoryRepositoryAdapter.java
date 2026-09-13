package com.skt.autopay.paymeansregistration.infra.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.skt.autopay.paymeansregistration.application.port.RealtmAuthHistoryPort;
import com.skt.autopay.paymeansregistration.domain.model.RealtmAuthStatus;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.PayMeansRealtmAuthentcEntity;
import com.skt.autopay.paymeansregistration.infra.persistence.mapper.PayMeansRealtmAuthentcMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 납부수단 실시간인증 이력 저장소 어댑터.
 *
 * <p>요청 시 INSERT(인증중) → 결과 UPDATE(성공/실패) → 성공 시 payMeansId 반영.
 */
@Repository
public class RealtmAuthHistoryRepositoryAdapter implements RealtmAuthHistoryPort {

    private final PayMeansRealtmAuthentcMapper mapper;

    public RealtmAuthHistoryRepositoryAdapter(PayMeansRealtmAuthentcMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long createRequested(RequestCommand command) {
        Long seq = generateSeq();
        PayMeansRealtmAuthentcEntity e = new PayMeansRealtmAuthentcEntity();
        e.setRealtmAuthentcSeq(seq);
        e.setTenantId(command.tenantId());
        e.setPayMthdCd(command.payMeansTypeCd());
        e.setAuthentcMthdCd(command.authentcMthdCd());
        e.setAuthentcStatCd(RealtmAuthStatus.REQUESTED.code()); // 01 인증중
        e.setAuthentcReqDtm(LocalDateTime.now());
        e.setPayInstCd(command.fincInstCd());
        e.setBankAcctCd(command.accountOrCardNo());
        mapper.insert(e);
        return seq;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void applyResult(Long realtmAuthentcSeq, String tenantId,
                            boolean success, String externalAuthKey,
                            String resultCode, String resultMessage) {
        // 재조회 없이 조건 UPDATE (REQUIRES_NEW 격리로 인한 "못 찾음" 방지)
        LambdaUpdateWrapper<PayMeansRealtmAuthentcEntity> u =
                new LambdaUpdateWrapper<PayMeansRealtmAuthentcEntity>()
                        .eq(PayMeansRealtmAuthentcEntity::getRealtmAuthentcSeq, realtmAuthentcSeq)
                        .eq(PayMeansRealtmAuthentcEntity::getTenantId, tenantId)
                        .set(PayMeansRealtmAuthentcEntity::getAuthentcStatCd,
                                success ? RealtmAuthStatus.SUCCESS.code() : RealtmAuthStatus.FAILED.code())
                        .set(PayMeansRealtmAuthentcEntity::getAuthentcCmplDtm, LocalDateTime.now())
                        .set(PayMeansRealtmAuthentcEntity::getExtnlAuthentcKey, externalAuthKey)
                        .set(PayMeansRealtmAuthentcEntity::getAuthentcResultCd, success ? "00" : "99")
                        .set(PayMeansRealtmAuthentcEntity::getAuthentcResultMsg, resultMessage)
                        .set(PayMeansRealtmAuthentcEntity::getExtnlAuthentcResultCd, resultCode)
                        .set(PayMeansRealtmAuthentcEntity::getExtnlAuthentcResultMsg, resultMessage);
        mapper.update(null, u);
    }

    @Override
    public void linkPayMeans(Long realtmAuthentcSeq, String tenantId, Long payMeansNo) {
        LambdaUpdateWrapper<PayMeansRealtmAuthentcEntity> u =
                new LambdaUpdateWrapper<PayMeansRealtmAuthentcEntity>()
                        .eq(PayMeansRealtmAuthentcEntity::getRealtmAuthentcSeq, realtmAuthentcSeq)
                        .eq(PayMeansRealtmAuthentcEntity::getTenantId, tenantId)
                        .set(PayMeansRealtmAuthentcEntity::getPayMeansId, payMeansNo);
        mapper.update(null, u);
    }

    private Long generateSeq() {
        return System.currentTimeMillis() * 1000 + ThreadLocalRandom.current().nextInt(1000);
    }
}
