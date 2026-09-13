package com.skt.autopay.paymeansregistration.application.command;

import com.skt.autopay.paymeansregistration.application.dto.PayMeansResult;
import com.skt.autopay.paymeansregistration.application.port.PayMeansRepositoryPort;
import com.skt.autopay.paymeansregistration.domain.model.PayMeans;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 납부수단 사용중지(U6)/해지(U7) 서비스.
 */
@Service
public class DisablePayMeansService {

    private final PayMeansRepositoryPort payMeansRepository;

    public DisablePayMeansService(PayMeansRepositoryPort payMeansRepository) {
        this.payMeansRepository = payMeansRepository;
    }

    /** U6 사용중지 (10 → 20). 신규 매핑 차단, 기존 PAY_ACCT 유지. */
    @Transactional
    public PayMeansResult disable(Long payMeansNo, String tenantId) {
        PayMeans m = load(payMeansNo, tenantId);
        m.suspend();
        payMeansRepository.update(m);
        return result(m);
    }

    /** 사용재개 (20 → 10). 정지된 수단을 다시 활성화. */
    @Transactional
    public PayMeansResult resume(Long payMeansNo, String tenantId) {
        PayMeans m = load(payMeansNo, tenantId);
        m.resume();
        payMeansRepository.update(m);
        return result(m);
    }

    /** U7 해지 (10/20 → 30). */
    @Transactional
    public PayMeansResult terminate(Long payMeansNo, String tenantId) {
        PayMeans m = load(payMeansNo, tenantId);
        m.terminate();
        payMeansRepository.update(m);
        return result(m);
    }

    private PayMeans load(Long payMeansNo, String tenantId) {
        return payMeansRepository.findByNo(payMeansNo, tenantId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "납부수단을 찾을 수 없습니다: " + payMeansNo));
    }

    private PayMeansResult result(PayMeans m) {
        return new PayMeansResult(
                m.getPayMeansNo(), m.getStatus().code(), m.getStatus().label(), null);
    }
}
