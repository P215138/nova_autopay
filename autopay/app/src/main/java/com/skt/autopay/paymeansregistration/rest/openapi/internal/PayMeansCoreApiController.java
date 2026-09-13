package com.skt.autopay.paymeansregistration.rest.openapi.internal;

import com.skt.autopay.paymeansregistration.application.command.AgentPayRequestService;
import com.skt.autopay.paymeansregistration.application.command.ChangePayMeansService;
import com.skt.autopay.paymeansregistration.application.command.DisablePayMeansService;
import com.skt.autopay.paymeansregistration.application.command.PayMeansRegistrationService;
import com.skt.autopay.paymeansregistration.application.dto.AgentPayApprovalCommand;
import com.skt.autopay.paymeansregistration.application.dto.AgentPayMapView;
import com.skt.autopay.paymeansregistration.application.dto.ChangePayMeansCommand;
import com.skt.autopay.paymeansregistration.application.dto.LinkExistingMeansCommand;
import com.skt.autopay.paymeansregistration.application.dto.PayMeansResult;
import com.skt.autopay.paymeansregistration.application.dto.PayMeansView;
import com.skt.autopay.paymeansregistration.application.dto.RegisterAgentPayMeansCommand;
import com.skt.autopay.paymeansregistration.application.dto.RegisterPayMeansCommand;
import com.skt.autopay.paymeansregistration.application.query.PayMeansQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 납부수단 관리 REST 컨트롤러.
 *
 * <p>설계서 §7 API 명세 기준.
 * <ul>
 *   <li>POST /api/autopay/pay-means                           U1 본인 등록</li>
 *   <li>GET  /api/autopay/pay-means?customerNo=               U3 지갑 목록 조회</li>
 *   <li>GET  /api/autopay/pay-means/{no}                      단건 조회 (payMeansId 단독)</li>
 *   <li>POST /api/autopay/pay-means/{no}/change               U4 별칭/연락처 정보변경</li>
 *   <li>POST /api/autopay/pay-means/{no}/disable              U6 사용중지</li>
 *   <li>POST /api/autopay/pay-means/{no}/terminate            U7 해지</li>
 *   <li>POST /api/autopay/pay-means/agent-requests            U9 대리납부 등록 요청</li>
 *   <li>POST /api/autopay/pay-means/agent-requests/{seqno}/complete  U10 비대면 2차 승인/거절</li>
 *   <li>GET  /api/autopay/pay-means/agent-maps?customerNo=    U11 대리납부 맵핑 조회</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/autopay/pay-means")
public class PayMeansCoreApiController {

    private final PayMeansRegistrationService registrationService;
    private final AgentPayRequestService agentPayRequestService;
    private final PayMeansQueryService queryService;
    private final ChangePayMeansService changeService;
    private final DisablePayMeansService disableService;

    public PayMeansCoreApiController(PayMeansRegistrationService registrationService,
                                     AgentPayRequestService agentPayRequestService,
                                     PayMeansQueryService queryService,
                                     ChangePayMeansService changeService,
                                     DisablePayMeansService disableService) {
        this.registrationService = registrationService;
        this.agentPayRequestService = agentPayRequestService;
        this.queryService = queryService;
        this.changeService = changeService;
        this.disableService = disableService;
    }

    /** U1 본인명의 납부수단 등록 */
    @PostMapping
    public PayMeansResult register(@RequestBody RegisterPayMeansCommand command) {
        return registrationService.registerByOwner(command);
    }

    /** U3 지갑 목록 조회 */
    @GetMapping
    public List<PayMeansView> wallet(@RequestParam("tenantId") String tenantId,
                                     @RequestParam("customerNo") String customerNo) {
        return queryService.findWallet(tenantId, customerNo);
    }

    /** 단건 조회 - 납부수단번호(payMeansId) 단독 키 */
    @GetMapping("/{no}")
    public PayMeansView findOne(@PathVariable("no") Long no) {
        return queryService.findOne(no);
    }

    /** U4 별칭/연락처 정보변경 */
    @PostMapping("/{no}/change")
    public PayMeansResult change(@PathVariable("no") Long no,
                                 @RequestBody ChangeRequest body) {
        return changeService.change(
                new ChangePayMeansCommand(body.tenantId(), no, body.payMeansNm()));
    }

    /** U6 사용중지 */
    @PostMapping("/{no}/disable")
    public PayMeansResult disable(@PathVariable("no") Long no,
                                  @RequestBody TenantRequest body) {
        return disableService.disable(no, body.tenantId());
    }

    /** 사용재개 (20 → 10) */
    @PostMapping("/{no}/resume")
    public PayMeansResult resume(@PathVariable("no") Long no,
                                 @RequestBody TenantRequest body) {
        return disableService.resume(no, body.tenantId());
    }

    /** U7 해지 */
    @PostMapping("/{no}/terminate")
    public PayMeansResult terminate(@PathVariable("no") Long no,
                                    @RequestBody TenantRequest body) {
        return disableService.terminate(no, body.tenantId());
    }

    /** U9 대리납부(타인명의) 등록 요청 - 대면/비대면 */
    @PostMapping("/agent-requests")
    public PayMeansResult registerAgent(@RequestBody RegisterAgentPayMeansCommand command) {
        return registrationService.registerByAgent(command);
    }

    /**
     * 기존 납부수단 재사용(대리납부) - 부모의 기존 수단을 자식이 사용하도록 연결.
     * 새 PayMeans를 만들지 않고, 금고에서 원본을 복호화해 재인증한 뒤 AgentPayMap만 추가.
     * 재인증 성공 + 소유주 동의요청 발송 후 요청(REQ) 상태로 반환.
     */
    @PostMapping("/{no}/agent-link")
    public PayMeansResult linkExisting(@PathVariable("no") Long no,
                                       @RequestBody AgentLinkRequest body) {
        return registrationService.linkExistingMeansToAgent(
                new LinkExistingMeansCommand(
                        body.tenantId(), no,
                        body.agentPayerCustomerNo(), body.bnfcPayerCustomerNo(),
                        body.relCatgCd(), body.ownerPhoneNo()));
    }

    /** U10 대리납부 비대면 2차 - 소유주 승인/거절 완료 처리 */
    @PostMapping("/agent-requests/{seqno}/complete")
    public PayMeansResult completeAgentConsent(@PathVariable("seqno") Long seqno,
                                               @RequestBody CompleteRequest body) {
        return agentPayRequestService.completeConsent(
                new AgentPayApprovalCommand(body.tenantId(), seqno, body.approved()));
    }

    /** 대리납부 관계 해지 - AgentPayMap 종료(TRM). PayMeans 자체는 유지 */
    @PostMapping("/agent-maps/{seqno}/terminate")
    public void terminateAgentMap(@PathVariable("seqno") Long seqno,
                                  @RequestBody TenantRequest body) {
        agentPayRequestService.terminateRelation(seqno, body.tenantId());
    }

    /** U11 대리납부 맵핑 조회 (피대리납부자=사용자 기준) */
    @GetMapping("/agent-maps")
    public List<AgentPayMapView> agentMaps(@RequestParam("tenantId") String tenantId,
                                           @RequestParam("customerNo") String customerNo) {
        return queryService.findAgentMapsByBeneficiary(tenantId, customerNo);
    }

    /** 대리납부 맵핑 조회 (대리납부자=소유주 기준) - 내가 남에게 빌려준 관계 전체 */
    @GetMapping("/agent-maps/as-owner")
    public List<AgentPayMapView> agentMapsAsOwner(@RequestParam("tenantId") String tenantId,
                                                  @RequestParam("customerNo") String customerNo) {
        return queryService.findAgentMapsByAgentPayer(tenantId, customerNo);
    }

    /** 소유주(대리납부자) 기준 승인대기 목록 조회 */
    @GetMapping("/agent-requests/pending")
    public List<AgentPayMapView> pendingConsents(@RequestParam("tenantId") String tenantId,
                                                 @RequestParam("customerNo") String customerNo) {
        return queryService.findPendingConsents(tenantId, customerNo);
    }

    // ---- 요청 바디 ----
    public record ChangeRequest(String tenantId, String payMeansNm) {
    }

    public record TenantRequest(String tenantId) {
    }

    public record CompleteRequest(String tenantId, boolean approved) {
    }

    /** 기존 수단 재사용 요청 바디 (납부수단번호는 경로 {no}) */
    public record AgentLinkRequest(String tenantId,
                                   String agentPayerCustomerNo,
                                   String bnfcPayerCustomerNo,
                                   String relCatgCd,
                                   String ownerPhoneNo) {
    }
}
