package com.skt.autopay.customer.rest;

import com.skt.autopay.customer.application.CustomerService;
import com.skt.autopay.customer.application.dto.CustomerView;
import com.skt.autopay.customer.application.dto.RegisterCustomerCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 고객 관리 REST 컨트롤러.
 *
 * <ul>
 *   <li>POST /api/autopay/customers        고객 등록</li>
 *   <li>GET  /api/autopay/customers        고객 목록 조회</li>
 *   <li>GET  /api/autopay/customers/{no}   고객 단건 조회</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/autopay/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerView register(@RequestBody RegisterCustomerCommand command) {
        return customerService.register(command);
    }

    @GetMapping
    public List<CustomerView> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{no}")
    public CustomerView findOne(@PathVariable("no") String customerNo) {
        return customerService.findOne(customerNo);
    }

    /** 주민번호로 고객 조회 */
    @GetMapping("/by-rrn")
    public CustomerView findByRrn(@RequestParam("rrn") String rrn) {
        return customerService.findByRrn(rrn);
    }
}
