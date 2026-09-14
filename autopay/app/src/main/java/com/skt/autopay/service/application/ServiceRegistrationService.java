package com.skt.autopay.service.application;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.skt.autopay.billing.infra.persistence.entity.BillingAccountEntity;
import com.skt.autopay.billing.infra.persistence.mapper.BillingAccountMapper;
import com.skt.autopay.customer.application.CustomerService;
import com.skt.autopay.customer.application.dto.CustomerView;
import com.skt.autopay.service.application.dto.RegisterServiceCommand;
import com.skt.autopay.service.application.dto.ServiceView;
import com.skt.autopay.service.infra.persistence.entity.ServiceEntity;
import com.skt.autopay.service.infra.persistence.mapper.ServiceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 서비스(이동전화) 등록/조회/상태변경 서비스.
 *
 * <ul>
 *   <li>등록: 서비스관리번호 채번(1로 시작 10자리) + 상태 AC(사용중)</li>
 *   <li>상태변경: AC 사용중 / SP 정지 / TG 해지</li>
 * </ul>
 */
@Service
public class ServiceRegistrationService {

    /** 상태코드 */
    public static final String STAT_ACTIVE = "AC";     // 사용중
    public static final String STAT_SUSPEND = "SP";    // 정지
    public static final String STAT_TERMINATE = "TG";  // 해지

    private final ServiceMapper serviceMapper;
    private final BillingAccountMapper billingAccountMapper;
    private final CustomerService customerService;

    public ServiceRegistrationService(ServiceMapper serviceMapper,
                                      BillingAccountMapper billingAccountMapper,
                                      CustomerService customerService) {
        this.serviceMapper = serviceMapper;
        this.billingAccountMapper = billingAccountMapper;
        this.customerService = customerService;
    }

    /**
     * 서비스번호(대체ID)로 소유 고객을 조회.
     * 서비스 → 청구계정(BILLING_ACCOUNT_NO) → 고객(CUSTOMER_NO) 순으로 추적한다.
     * 테넌트는 SKT 고정 컨텍스트에서 서비스번호가 유일하다고 가정(중복 시 최근 등록건).
     */
    public CustomerView findCustomerByServiceNo(String serviceNoAltrnateId) {
        ServiceEntity svc = serviceMapper.selectOne(
                new QueryWrapper<ServiceEntity>()
                        .eq("SERVICE_NO_ALTRNATE_ID", serviceNoAltrnateId)
                        .orderByDesc("FIRST_REGIST_DTM")
                        .last("LIMIT 1"));
        if (svc == null) {
            throw new IllegalArgumentException("해당 서비스번호의 서비스를 찾을 수 없습니다.");
        }
        BillingAccountEntity ba = billingAccountMapper.selectById(svc.getBillingAccountNo());
        if (ba == null || ba.getCustomerNo() == null) {
            throw new IllegalStateException("서비스에 연결된 청구계정/고객 정보를 찾을 수 없습니다.");
        }
        return customerService.findOne(ba.getCustomerNo());
    }

    /** 서비스 등록 - 채번 + 상태 AC(사용중) */
    @Transactional
    public ServiceView register(RegisterServiceCommand cmd) {
        ServiceEntity e = new ServiceEntity();
        e.setServiceMgmtNo(generateServiceMgmtNo());
        e.setTenantId(cmd.tenantId());
        e.setBillingAccountNo(cmd.billingAccountNo());
        e.setServiceNoAltrnateId(cmd.serviceNoAltrnateId());
        e.setServiceStatCd(STAT_ACTIVE);
        serviceMapper.insert(e);
        return toView(e);
    }

    /** 전체 목록 (최근 등록순) */
    public List<ServiceView> findAll() {
        return serviceMapper.selectList(
                        new QueryWrapper<ServiceEntity>().orderByDesc("FIRST_REGIST_DTM"))
                .stream().map(this::toView).toList();
    }

    /** 청구계정번호 기준 서비스 목록 */
    public List<ServiceView> findByBillingAccount(String tenantId, String billingAccountNo) {
        return serviceMapper.selectList(
                        new QueryWrapper<ServiceEntity>()
                                .eq("TENANT_ID", tenantId)
                                .eq("BILLING_ACCOUNT_NO", billingAccountNo)
                                .orderByDesc("FIRST_REGIST_DTM"))
                .stream().map(this::toView).toList();
    }

    /** 정지 (AC -> SP) */
    @Transactional
    public ServiceView suspend(String serviceMgmtNo) {
        return changeStatus(serviceMgmtNo, STAT_ACTIVE, STAT_SUSPEND, "사용중 상태만 정지할 수 있습니다.");
    }

    /** 재개 (SP -> AC) */
    @Transactional
    public ServiceView resume(String serviceMgmtNo) {
        return changeStatus(serviceMgmtNo, STAT_SUSPEND, STAT_ACTIVE, "정지 상태만 재개할 수 있습니다.");
    }

    /** 해지 (AC/SP -> TG) */
    @Transactional
    public ServiceView terminate(String serviceMgmtNo) {
        ServiceEntity e = load(serviceMgmtNo);
        if (!STAT_ACTIVE.equals(e.getServiceStatCd()) && !STAT_SUSPEND.equals(e.getServiceStatCd())) {
            throw new IllegalStateException("해지 불가 상태입니다: " + e.getServiceStatCd());
        }
        e.setServiceStatCd(STAT_TERMINATE);
        serviceMapper.updateById(e);
        return toView(e);
    }

    /** 공통 상태 전이 (기대 상태 검증 후 변경) */
    private ServiceView changeStatus(String serviceMgmtNo, String expected, String next, String errMsg) {
        ServiceEntity e = load(serviceMgmtNo);
        if (!expected.equals(e.getServiceStatCd())) {
            throw new IllegalStateException(errMsg + " (현재: " + statLabel(e.getServiceStatCd()) + ")");
        }
        e.setServiceStatCd(next);
        serviceMapper.updateById(e);
        return toView(e);
    }

    private ServiceEntity load(String serviceMgmtNo) {
        ServiceEntity e = serviceMapper.selectById(serviceMgmtNo);
        if (e == null) {
            throw new IllegalArgumentException("서비스를 찾을 수 없습니다: " + serviceMgmtNo);
        }
        return e;
    }

    /** 1로 시작하는 10자리 서비스관리번호 채번 (뒤 9자리 랜덤) */
    private String generateServiceMgmtNo() {
        int rest = ThreadLocalRandom.current().nextInt(0, 1_000_000_000);
        return "1" + String.format("%09d", rest);
    }

    private ServiceView toView(ServiceEntity e) {
        return new ServiceView(
                e.getServiceMgmtNo(),
                e.getTenantId(),
                e.getBillingAccountNo(),
                e.getServiceNoAltrnateId(),
                e.getServiceStatCd(),
                statLabel(e.getServiceStatCd()));
    }

    /** 상태코드 -> 상태명 */
    private String statLabel(String cd) {
        if (STAT_ACTIVE.equals(cd)) return "사용중";
        if (STAT_SUSPEND.equals(cd)) return "정지";
        if (STAT_TERMINATE.equals(cd)) return "해지";
        return cd;
    }
}
