package com.skt.autopay.billing.application;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.skt.autopay.billing.application.dto.BillingAccountView;
import com.skt.autopay.billing.application.dto.RegisterBillingAccountCommand;
import com.skt.autopay.billing.infra.persistence.entity.BillingAccountEntity;
import com.skt.autopay.billing.infra.persistence.mapper.BillingAccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 청구계정 등록/조회 서비스.
 */
@Service
public class BillingAccountService {

    private final BillingAccountMapper billingAccountMapper;

    public BillingAccountService(BillingAccountMapper billingAccountMapper) {
        this.billingAccountMapper = billingAccountMapper;
    }

    @Transactional
    public BillingAccountView register(RegisterBillingAccountCommand cmd) {
        BillingAccountEntity e = new BillingAccountEntity();
        e.setBillingAccountNo(generateBillingAccountNo());
        e.setTenantId(cmd.tenantId());
        e.setCustomerNo(cmd.customerNo());
        e.setPayClCode(cmd.payClCode());
        e.setBillingCycleCode(cmd.billingCycleCode());
        e.setUseYn("Y");
        billingAccountMapper.insert(e);
        return toView(e);
    }

    public List<BillingAccountView> findAll() {
        return billingAccountMapper.selectList(
                        new QueryWrapper<BillingAccountEntity>().orderByDesc("FIRST_REGIST_DTM"))
                .stream().map(this::toView).toList();
    }

    /** 고객번호 기준 청구계정 목록 */
    public List<BillingAccountView> findByCustomer(String tenantId, String customerNo) {
        return billingAccountMapper.selectList(
                        new QueryWrapper<BillingAccountEntity>()
                                .eq("TENANT_ID", tenantId)
                                .eq("CUSTOMER_NO", customerNo))
                .stream().map(this::toView).toList();
    }

    /** 2로 시작하는 10자리 청구계정번호 채번 (뒤 9자리 랜덤) */
    private String generateBillingAccountNo() {
        int rest = ThreadLocalRandom.current().nextInt(0, 1_000_000_000);
        return "2" + String.format("%09d", rest);
    }

    private BillingAccountView toView(BillingAccountEntity e) {
        return new BillingAccountView(
                e.getBillingAccountNo(),
                e.getTenantId(),
                e.getCustomerNo(),
                e.getPayClCode(),
                e.getBillingCycleCode(),
                e.getUseYn());
    }
}
