package com.skt.autopay.paymeansregistration.application.query;

import com.skt.autopay.paymeansregistration.application.dto.AgentPayMapView;
import com.skt.autopay.paymeansregistration.application.dto.PayMeansView;
import com.skt.autopay.paymeansregistration.application.port.AccountVaultPort;
import com.skt.autopay.paymeansregistration.application.port.AgentPayMapRepositoryPort;
import com.skt.autopay.paymeansregistration.application.port.PayMeansRepositoryPort;
import com.skt.autopay.paymeansregistration.domain.model.AgentPayMap;
import com.skt.autopay.paymeansregistration.domain.model.PayMeans;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 납부수단 조회 서비스 (U3 지갑 목록/단건, U11 대리납부 맵핑 조회).
 */
@Service
public class PayMeansQueryService {

    private final PayMeansRepositoryPort payMeansRepository;
    private final AgentPayMapRepositoryPort agentPayMapRepository;
    private final AccountVaultPort accountVault;

    public PayMeansQueryService(PayMeansRepositoryPort payMeansRepository,
                                AgentPayMapRepositoryPort agentPayMapRepository,
                                AccountVaultPort accountVault) {
        this.payMeansRepository = payMeansRepository;
        this.agentPayMapRepository = agentPayMapRepository;
        this.accountVault = accountVault;
    }

    /** U3 지갑 목록 조회 (고객 기준) */
    public List<PayMeansView> findWallet(String tenantId, String customerNo) {
        return payMeansRepository.findByCustomer(tenantId, customerNo).stream()
                .map(this::toView)
                .toList();
    }

    /** 단건 조회 - 납부수단번호(PK) 단독 */
    public PayMeansView findOne(Long payMeansNo) {
        PayMeans m = payMeansRepository.findById(payMeansNo)
                .orElseThrow(() -> new IllegalArgumentException(
                        "납부수단을 찾을 수 없습니다: " + payMeansNo));
        return toView(m);
    }

    /** U11 대리납부 맵핑 조회 - 피대리납부자(수단 사용자) 기준 */
    public List<AgentPayMapView> findAgentMapsByBeneficiary(String tenantId, String bnfcPayerCustomerNo) {
        return agentPayMapRepository.findByBeneficiary(tenantId, bnfcPayerCustomerNo).stream()
                .map(this::toAgentView)
                .toList();
    }

    /** U11 대리납부 맵핑 조회 - 대리납부자(수단 소유주) 기준 */
    public List<AgentPayMapView> findAgentMapsByAgentPayer(String tenantId, String agentPayerCustomerNo) {
        return agentPayMapRepository.findByAgentPayer(tenantId, agentPayerCustomerNo).stream()
                .map(this::toAgentView)
                .toList();
    }

    /** 소유주(대리납부자) 기준 승인대기(요청 상태) 목록 */
    public List<AgentPayMapView> findPendingConsents(String tenantId, String agentPayerCustomerNo) {
        return agentPayMapRepository.findByAgentPayer(tenantId, agentPayerCustomerNo).stream()
                .filter(m -> m.getStatus() == com.skt.autopay.paymeansregistration.domain.model.AgentPayMapStatus.REQ)
                .map(this::toAgentView)
                .toList();
    }

    private PayMeansView toView(PayMeans m) {
        return new PayMeansView(
                m.getPayMeansNo(),
                m.getCustomerNo(),
                m.getType() != null ? m.getType().code() : null,
                m.getType() != null ? m.getType().label() : null,
                m.getStatus() != null ? m.getStatus().code() : null,
                m.getStatus() != null ? m.getStatus().label() : null,
                m.getPayMeansNm(),
                m.getFincInstCd(),
                m.getHolderNm(),
                m.getBankacctCardAltrnateId(),
                maskedNoOf(m.getBankacctCardAltrnateId()));
    }

    /**
     * 대체ID로 금고에서 원본을 복호화한 뒤 마스킹하여 반환.
     * 원본은 절대 그대로 노출하지 않는다. 금고에 없으면 null.
     */
    private String maskedNoOf(Long bankacctCardAltrnateId) {
        if (bankacctCardAltrnateId == null) {
            return null;
        }
        return accountVault.resolve(bankacctCardAltrnateId)
                .map(this::mask)
                .orElse(null);
    }

    /**
     * 계좌/카드번호 마스킹. 앞 4자리와 뒤 4자리만 노출, 중간은 * 처리.
     * 8자리 이하 등 짧으면 뒤 4자리만 노출한다.
     */
    private String mask(String no) {
        if (no == null || no.isBlank()) {
            return null;
        }
        String s = no.trim();
        int len = s.length();
        if (len <= 4) {
            return "*".repeat(len);
        }
        if (len <= 8) {
            // 뒤 4자리만 노출
            return "*".repeat(len - 4) + s.substring(len - 4);
        }
        String head = s.substring(0, 4);
        String tail = s.substring(len - 4);
        return head + "*".repeat(len - 8) + tail;
    }

    private AgentPayMapView toAgentView(AgentPayMap m) {
        return new AgentPayMapView(
                m.getAgentPayMapSeqno(),
                m.getAgentPayerCustomerNo(),
                m.getBnfcPayerCustomerNo(),
                m.getPayMeansNo(),
                m.getRelCatgCd(),
                m.getStatus() != null ? m.getStatus().code() : null,
                m.getStatus() != null ? m.getStatus().label() : null,
                m.getValidStartDtm(),
                m.getValidEndDtm());
    }
}
