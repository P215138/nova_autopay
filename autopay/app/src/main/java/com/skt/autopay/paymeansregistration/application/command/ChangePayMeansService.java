package com.skt.autopay.paymeansregistration.application.command;

import com.skt.autopay.paymeansregistration.application.dto.ChangePayMeansCommand;
import com.skt.autopay.paymeansregistration.application.dto.PayMeansResult;
import com.skt.autopay.paymeansregistration.application.port.PayMeansRepositoryPort;
import com.skt.autopay.paymeansregistration.domain.model.PayMeans;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 납부수단 정보변경 서비스 (U4 별칭/연락처).
 */
@Service
public class ChangePayMeansService {

    private final PayMeansRepositoryPort payMeansRepository;

    public ChangePayMeansService(PayMeansRepositoryPort payMeansRepository) {
        this.payMeansRepository = payMeansRepository;
    }

    @Transactional
    public PayMeansResult change(ChangePayMeansCommand cmd) {
        PayMeans m = payMeansRepository.findByNo(cmd.payMeansNo(), cmd.tenantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "납부수단을 찾을 수 없습니다: " + cmd.payMeansNo()));
        m.changeInfo(cmd.payMeansNm());
        payMeansRepository.update(m);
        return new PayMeansResult(
                m.getPayMeansNo(), m.getStatus().code(), m.getStatus().label(), null);
    }
}
