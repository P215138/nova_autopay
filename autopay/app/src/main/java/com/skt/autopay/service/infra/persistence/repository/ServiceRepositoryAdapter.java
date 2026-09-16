package com.skt.autopay.service.infra.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.skt.autopay.service.application.port.ServiceRepositoryPort;
import com.skt.autopay.service.domain.model.Service;
import com.skt.autopay.service.domain.model.ServiceStatus;
import com.skt.autopay.service.infra.persistence.entity.ServiceEntity;
import com.skt.autopay.service.infra.persistence.mapper.ServiceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 서비스 저장소 포트 구현 어댑터.
 *
 * <p>MyBatis-Plus(ServiceMapper)를 사용하고, 엔티티 &lt;-&gt; 도메인 모델을 변환한다.
 * 서비스관리번호 채번(1로 시작 10자리)도 인프라 책임으로 여기서 수행한다.
 */
@Repository
public class ServiceRepositoryAdapter implements ServiceRepositoryPort {

    private final ServiceMapper serviceMapper;

    public ServiceRepositoryAdapter(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    @Override
    public Service save(Service service) {
        // 신규 저장 시 채번 (1로 시작 10자리)
        if (service.getServiceMgmtNo() == null) {
            service.assignServiceMgmtNo(generateServiceMgmtNo());
        }
        serviceMapper.insert(toEntity(service));
        return service;
    }

    @Override
    public void update(Service service) {
        serviceMapper.updateById(toEntity(service));
    }

    @Override
    public Optional<Service> findById(String serviceMgmtNo) {
        ServiceEntity e = serviceMapper.selectById(serviceMgmtNo);
        return Optional.ofNullable(e).map(this::toDomain);
    }

    @Override
    public List<Service> findAll() {
        return serviceMapper.selectList(
                        new QueryWrapper<ServiceEntity>().orderByDesc("FIRST_REGIST_DTM"))
                .stream().map(this::toDomain).toList();
    }

    @Override
    public List<Service> findByBillingAccount(String tenantId, String billingAccountNo) {
        return serviceMapper.selectList(
                        new QueryWrapper<ServiceEntity>()
                                .eq("TENANT_ID", tenantId)
                                .eq("BILLING_ACCOUNT_NO", billingAccountNo)
                                .orderByDesc("FIRST_REGIST_DTM"))
                .stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Service> findByServiceNo(String serviceNoAltrnateId) {
        ServiceEntity e = serviceMapper.selectOne(
                new QueryWrapper<ServiceEntity>()
                        .eq("SERVICE_NO_ALTRNATE_ID", serviceNoAltrnateId)
                        .orderByDesc("FIRST_REGIST_DTM")
                        .last("LIMIT 1"));
        return Optional.ofNullable(e).map(this::toDomain);
    }

    /** 1로 시작하는 10자리 서비스관리번호 채번 (뒤 9자리 랜덤) */
    private String generateServiceMgmtNo() {
        int rest = ThreadLocalRandom.current().nextInt(0, 1_000_000_000);
        return "1" + String.format("%09d", rest);
    }

    private ServiceEntity toEntity(Service s) {
        ServiceEntity e = new ServiceEntity();
        e.setServiceMgmtNo(s.getServiceMgmtNo());
        e.setTenantId(s.getTenantId());
        e.setBillingAccountNo(s.getBillingAccountNo());
        e.setServiceNoAltrnateId(s.getServiceNoAltrnateId());
        e.setServiceStatCd(s.getStatus() != null ? s.getStatus().code() : null);
        return e;
    }

    private Service toDomain(ServiceEntity e) {
        return Service.reconstitute(
                e.getServiceMgmtNo(),
                e.getTenantId(),
                e.getBillingAccountNo(),
                e.getServiceNoAltrnateId(),
                e.getServiceStatCd() != null ? ServiceStatus.fromCode(e.getServiceStatCd()) : null);
    }
}
