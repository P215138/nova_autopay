package com.skt.autopay.billing.rest;

import com.skt.autopay.billing.application.BillingAccountService;
import com.skt.autopay.billing.application.dto.BillingAccountView;
import com.skt.autopay.billing.application.dto.RegisterBillingAccountCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 청구계정 관리 REST 컨트롤러.
 *
 * <ul>
 *   <li>POST /api/autopay/billing-accounts               청구계정 등록</li>
 *   <li>GET  /api/autopay/billing-accounts               전체 목록</li>
 *   <li>GET  /api/autopay/billing-accounts?tenantId&customerNo  고객 기준 목록</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/autopay/billing-accounts")
public class BillingAccountController {

    private final BillingAccountService billingAccountService;

    public BillingAccountController(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;
    }

    @PostMapping
    public BillingAccountView register(@RequestBody RegisterBillingAccountCommand command) {
        return billingAccountService.register(command);
    }

    @GetMapping
    public List<BillingAccountView> list(
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "customerNo", required = false) String customerNo) {
        if (tenantId != null && customerNo != null) {
            return billingAccountService.findByCustomer(tenantId, customerNo);
        }
        return billingAccountService.findAll();
    }
}
