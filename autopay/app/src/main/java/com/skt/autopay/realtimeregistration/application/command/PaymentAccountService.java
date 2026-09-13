package com.skt.autopay.realtimeregistration.application.command;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.skt.autopay.realtimeregistration.application.dto.PaymentAccountView;
import com.skt.autopay.realtimeregistration.application.dto.RegisterPaymentAccountCommand;
import com.skt.autopay.realtimeregistration.infra.persistence.entity.PayAcctEntity;
import com.skt.autopay.realtimeregistration.infra.persistence.mapper.PayAcctMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 납부계정(PAY_ACCT) 등록/조회 서비스.
 *
 * <p>청구계정 1 : 납부계정 N. 납부계정에 메인/예비 납부수단을 연결한다.
 * 납부계정번호는 3으로 시작하는 10자리로 채번한다.
 */
@Service
public class PaymentAccountService {

    private final PayAcctMapper payAcctMapper;

    public PaymentAccountService(PayAcctMapper payAcctMapper) {
        this.payAcctMapper = payAcctMapper;
    }

    @Transactional
    public PaymentAccountView register(RegisterPaymentAccountCommand cmd) {
        PayAcctEntity e = new PayAcctEntity();
        e.setPayAcctNo(generatePayAcctNo());
        e.setTenantId(cmd.tenantId());
        e.setPayCatgCd(cmd.payCatgCd() != null ? cmd.payCatgCd() : "01");
        e.setInvoiceAcctNo(cmd.invoiceAcctNo());
        e.setPayMthdCd(cmd.payMthdCd());
        e.setPayCycleCd(cmd.payCycleCd());
        e.setPayCycleDayCd(cmd.payCycleDayCd());
        e.setPayMeansNo(cmd.payMeansNo());
        // 예비납부수단은 PK 구성요소라 null 불가 → 없으면 0
        e.setReservePayMeansNo(cmd.reservePayMeansNo() != null ? cmd.reservePayMeansNo() : 0L);
        e.setPayAcctStatCd("10"); // 활성
        payAcctMapper.insert(e);
        return toView(e);
    }

    public List<PaymentAccountView> findAll() {
        return payAcctMapper.selectList(
                        new QueryWrapper<PayAcctEntity>().orderByDesc("FIRST_REGIST_DTM"))
                .stream().map(this::toView).toList();
    }

    /** 청구계정번호 기준 납부계정 목록 (1:N) */
    public List<PaymentAccountView> findByInvoiceAcct(String tenantId, Long invoiceAcctNo) {
        return payAcctMapper.selectList(
                        new QueryWrapper<PayAcctEntity>()
                                .eq("TENANT_ID", tenantId)
                                .eq("INVOICE_ACCT_NO", invoiceAcctNo))
                .stream().map(this::toView).toList();
    }

    /** 3으로 시작하는 10자리 납부계정번호 채번 */
    private Long generatePayAcctNo() {
        int rest = ThreadLocalRandom.current().nextInt(0, 1_000_000_000);
        return Long.parseLong("3" + String.format("%09d", rest));
    }

    private PaymentAccountView toView(PayAcctEntity e) {
        return new PaymentAccountView(
                e.getPayAcctNo(),
                e.getTenantId(),
                e.getPayCatgCd(),
                e.getInvoiceAcctNo(),
                e.getPayMthdCd(),
                e.getPayCycleCd(),
                e.getPayCycleDayCd(),
                e.getPayMeansNo(),
                e.getReservePayMeansNo(),
                e.getPayAcctStatCd());
    }
}
