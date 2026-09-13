package com.skt.autopay.paymeansregistration.application.port;

import com.skt.autopay.paymeansregistration.domain.model.PayMeans;

import java.util.List;
import java.util.Optional;

/**
 * 납부수단 저장소 포트.
 */
public interface PayMeansRepositoryPort {

    /** 신규 저장 후 채번된 납부수단번호를 도메인에 반영하여 반환 */
    PayMeans save(PayMeans payMeans);

    /** 상태/정보 변경 반영 */
    void update(PayMeans payMeans);

    Optional<PayMeans> findByNo(Long payMeansNo, String tenantId);

    /** 납부수단번호(PK) 단독 조회 */
    Optional<PayMeans> findById(Long payMeansNo);

    /** 고객의 지갑 목록 조회 */
    List<PayMeans> findByCustomer(String tenantId, String customerNo);

    /**
     * 동일 계좌/카드(계좌카드대체ID)가 유효 상태(등록중01/인증대기02/활성10)로
     * 이미 등록돼 있는지 확인. 본인·대리 무관 1건만 허용하기 위한 중복 체크.
     */
    boolean existsActiveByAltrnateId(String tenantId, Long bankacctCardAltrnateId);
}
