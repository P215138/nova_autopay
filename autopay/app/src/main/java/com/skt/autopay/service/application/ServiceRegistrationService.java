package com.skt.autopay.service.application;

import com.skt.autopay.billing.infra.persistence.entity.BillingAccountEntity;
import com.skt.autopay.billing.infra.persistence.mapper.BillingAccountMapper;
import com.skt.autopay.customer.application.CustomerService;
import com.skt.autopay.customer.application.dto.CustomerView;
import com.skt.autopay.service.application.dto.RegisterServiceCommand;
import com.skt.autopay.service.application.dto.ServiceView;
import com.skt.autopay.service.application.port.ServiceRepositoryPort;
import com.skt.autopay.service.domain.model.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 서비스(이동전화) 등록/조회/상태변경 유스케이스.
 *
 * <p>헥사고날: 영속 기술을 직접 알지 않고 {@link ServiceRepositoryPort} 에만 의존한다.
 * 상태 전이 규칙은 도메인 모델 {@link Service} 가 소유한다.
 */
@org.springframework.stereotype.Service
public class ServiceRegistrationService {

    private final ServiceRepositoryPort serviceRepository;
    private final BillingAccountMapper billingAccountMapper;
    private final CustomerService customerService;

    public ServiceRegistrationService(ServiceRepositoryPort serviceRepository,
                                      BillingAccountMapper billingAccountMapper,
                                      CustomerService customerService) {
        this.serviceRepository = serviceRepository;
        this.billingAccountMapper = billingAccountMapper;
        this.customerService = customerService;
    }

    /** 서비스 등록 - 상태 AC(사용중). 채번은 어댑터가 수행. */
    @Transactional
    public ServiceView register(RegisterServiceCommand cmd) {
        Service service = Service.register(
                cmd.tenantId(), cmd.billingAccountNo(), cmd.serviceNoAltrnateId());
        service = serviceRepository.save(service);
        return toView(service);
    }

    /** 전체 목록 (최근 등록순) */
    public List<ServiceView> findAll() {
        return serviceRepository.findAll().stream().map(this::toView).toList();
    }

    /** 청구계정번호 기준 서비스 목록 */
    public List<ServiceView> findByBillingAccount(String tenantId, String billingAccountNo) {
        return serviceRepository.findByBillingAccount(tenantId, billingAccountNo)
                .stream().map(this::toView).toList();
    }

    /** 정지 (AC -> SP) */
    @Transactional
    public ServiceView suspend(String serviceMgmtNo) {
        Service s = load(serviceMgmtNo);
        s.suspend();
        serviceRepository.update(s);
        return toView(s);
    }

    /** 재개 (SP -> AC) */
    @Transactional
    public ServiceView resume(String serviceMgmtNo) {
        Service s = load(serviceMgmtNo);
        s.resume();
        serviceRepository.update(s);
        return toView(s);
    }

    /** 해지 (AC/SP -> TG) */
    @Transactional
    public ServiceView terminate(String serviceMgmtNo) {
        Service s = load(serviceMgmtNo);
        s.terminate();
        serviceRepository.update(s);
        return toView(s);
    }

    /**
     * 서비스번호(대체ID)로 소유 고객을 조회.
     * 서비스 -> 청구계정(BILLING_ACCOUNT_NO) -> 고객(CUSTOMER_NO) 순으로 추적.
     */
    public CustomerView findCustomerByServiceNo(String serviceNoAltrnateId) {
        Service svc = serviceRepository.findByServiceNo(serviceNoAltrnateId)
                .orElseThrow(() -> new IllegalArgumentException("해당 서비스번호의 서비스를 찾을 수 없습니다."));
        BillingAccountEntity ba = billingAccountMapper.selectById(svc.getBillingAccountNo());
        if (ba == null || ba.getCustomerNo() == null) {
            throw new IllegalStateException("서비스에 연결된 청구계정/고객 정보를 찾을 수 없습니다.");
        }
        return customerService.findOne(ba.getCustomerNo());
    }

    private Service load(String serviceMgmtNo) {
        return serviceRepository.findById(serviceMgmtNo)
                .orElseThrow(() -> new IllegalArgumentException("서비스를 찾을 수 없습니다: " + serviceMgmtNo));
    }

    private ServiceView toView(Service s) {
        return new ServiceView(
                s.getServiceMgmtNo(),
                s.getTenantId(),
                s.getBillingAccountNo(),
                s.getServiceNoAltrnateId(),
                s.getStatus() != null ? s.getStatus().code() : null,
                s.getStatus() != null ? s.getStatus().label() : null);
    }
}
