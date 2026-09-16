package com.skt.autopay.service.application.port;

import com.skt.autopay.service.domain.model.Service;

import java.util.List;
import java.util.Optional;

/**
 * 서비스(이동전화) 저장소 포트.
 *
 * <p>도메인/유스케이스는 이 포트에만 의존하고, 실제 영속 기술(MyBatis-Plus)은
 * infra 의 어댑터가 구현한다. (헥사고날 - 의존성 역전)
 */
public interface ServiceRepositoryPort {

    /** 신규 저장 후 채번된 서비스관리번호를 도메인에 반영하여 반환 */
    Service save(Service service);

    /** 상태 변경 반영 */
    void update(Service service);

    /** 서비스관리번호(PK) 단건 조회 */
    Optional<Service> findById(String serviceMgmtNo);

    /** 전체 목록 (최근 등록순) */
    List<Service> findAll();

    /** 청구계정번호 기준 목록 */
    List<Service> findByBillingAccount(String tenantId, String billingAccountNo);

    /** 서비스번호(대체ID)로 단건 조회 (중복 시 최근 등록건) */
    Optional<Service> findByServiceNo(String serviceNoAltrnateId);
}
