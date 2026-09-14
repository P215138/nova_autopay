package com.skt.autopay.service.rest;

import com.skt.autopay.customer.application.dto.CustomerView;
import com.skt.autopay.service.application.ServiceRegistrationService;
import com.skt.autopay.service.application.dto.RegisterServiceCommand;
import com.skt.autopay.service.application.dto.ServiceView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 서비스(이동전화) 관리 REST 컨트롤러.
 *
 * <ul>
 *   <li>POST /api/autopay/services                       서비스 등록</li>
 *   <li>GET  /api/autopay/services                       전체 목록</li>
 *   <li>GET  /api/autopay/services?tenantId&billingAccountNo  청구계정 기준 목록</li>
 *   <li>POST /api/autopay/services/{no}/suspend          정지 (AC->SP)</li>
 *   <li>POST /api/autopay/services/{no}/resume           재개 (SP->AC)</li>
 *   <li>POST /api/autopay/services/{no}/terminate        해지 (AC/SP->TG)</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/autopay/services")
public class ServiceController {

    private final ServiceRegistrationService serviceService;

    public ServiceController(ServiceRegistrationService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping
    public ServiceView register(@RequestBody RegisterServiceCommand command) {
        return serviceService.register(command);
    }

    @GetMapping
    public List<ServiceView> list(
            @RequestParam(value = "tenantId", required = false) String tenantId,
            @RequestParam(value = "billingAccountNo", required = false) String billingAccountNo) {
        if (tenantId != null && billingAccountNo != null) {
            return serviceService.findByBillingAccount(tenantId, billingAccountNo);
        }
        return serviceService.findAll();
    }

    @PostMapping("/{no}/suspend")
    public ServiceView suspend(@PathVariable("no") String no) {
        return serviceService.suspend(no);
    }

    @PostMapping("/{no}/resume")
    public ServiceView resume(@PathVariable("no") String no) {
        return serviceService.resume(no);
    }

    @PostMapping("/{no}/terminate")
    public ServiceView terminate(@PathVariable("no") String no) {
        return serviceService.terminate(no);
    }

    /** 서비스번호(대체ID)로 소유 고객 조회 (납부수단 등록 화면의 서비스번호 조회용) */
    @GetMapping("/by-service-no")
    public CustomerView findCustomerByServiceNo(@RequestParam("serviceNo") String serviceNo) {
        return serviceService.findCustomerByServiceNo(serviceNo);
    }
}
