package com.skt.autopay.realtimeregistration.rest.openapi.internal;

import com.skt.autopay.realtimeregistration.application.command.PaymentAccountService;
import com.skt.autopay.realtimeregistration.application.dto.PaymentAccountView;
import com.skt.autopay.realtimeregistration.application.dto.RegisterPaymentAccountCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 납부계정 관리 REST 컨트롤러.
 *
 * <ul>
 *   <li>POST /api/autopay/payment-accounts                      납부계정 등록</li>
 *   <li>GET  /api/autopay/payment-accounts                      전체 목록</li>
 *   <li>GET  /api/autopay/payment-accounts?tenantId&invoiceAcctNo  청구계정 기준 목록</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/autopay/payment-accounts")
public class PaymentAccountController {

    private final PaymentAccountService paymentAccountService;

    public PaymentAccountController(PaymentAccountService paymentAccountService) {
        this.paymentAccountService = paymentAccountService;
    }

    @PostMapping
    public PaymentAccountView register(@RequestBody RegisterPaymentAccountCommand command) {
        return paymentAccountService.register(command);
    }

    @GetMapping
    public List<PaymentAccountView> list(
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "invoiceAcctNo", required = false) Long invoiceAcctNo) {
        if (tenantId != null && invoiceAcctNo != null) {
            return paymentAccountService.findByInvoiceAcct(tenantId, invoiceAcctNo);
        }
        return paymentAccountService.findAll();
    }
}
