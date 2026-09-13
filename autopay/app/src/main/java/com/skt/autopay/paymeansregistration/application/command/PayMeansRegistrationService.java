package com.skt.autopay.paymeansregistration.application.command;

import com.skt.autopay.paymeansregistration.application.dto.LinkExistingMeansCommand;
import com.skt.autopay.paymeansregistration.application.dto.PayMeansResult;
import com.skt.autopay.paymeansregistration.application.dto.RegisterAgentPayMeansCommand;
import com.skt.autopay.paymeansregistration.application.dto.RegisterPayMeansCommand;
import com.skt.autopay.paymeansregistration.application.port.AccountVaultPort;
import com.skt.autopay.paymeansregistration.application.port.AgentPayMapRepositoryPort;
import com.skt.autopay.paymeansregistration.application.port.FinancialAuthGatewayPort;
import com.skt.autopay.paymeansregistration.application.port.PayMeansRepositoryPort;
import com.skt.autopay.paymeansregistration.application.port.RealtmAuthHistoryPort;
import com.skt.autopay.paymeansregistration.application.port.SmsAuthGatewayPort;
import com.skt.autopay.paymeansregistration.domain.model.AgentPayMap;
import com.skt.autopay.paymeansregistration.domain.model.AgentRegistrationType;
import com.skt.autopay.paymeansregistration.domain.model.PayMeans;
import com.skt.autopay.paymeansregistration.domain.model.PayMeansStatus;
import com.skt.autopay.paymeansregistration.domain.model.PayMeansType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 납부수단 등록 서비스 (U1 본인 / U2·U9 대리인).
 *
 * <p>auth-first: 금융기관 실시간인증을 먼저 수행하고, 성공한 경우에만 저장한다.
 * <ul>
 *   <li>본인 / 대리인 대면 : 인증 성공 → 활성(10)</li>
 *   <li>대리인 비대면      : 인증 성공 → 등록중(01) + AgentPayMap(요청) + 소유주 SMS 동의요청</li>
 * </ul>
 */
@Service
public class PayMeansRegistrationService {

    private final FinancialAuthGatewayPort financialAuth;
    private final SmsAuthGatewayPort smsAuth;
    private final PayMeansRepositoryPort payMeansRepository;
    private final AgentPayMapRepositoryPort agentPayMapRepository;
    private final RealtmAuthHistoryPort realtmAuthHistory;
    private final AccountVaultPort accountVault;

    public PayMeansRegistrationService(FinancialAuthGatewayPort financialAuth,
                                       SmsAuthGatewayPort smsAuth,
                                       PayMeansRepositoryPort payMeansRepository,
                                       AgentPayMapRepositoryPort agentPayMapRepository,
                                       RealtmAuthHistoryPort realtmAuthHistory,
                                       AccountVaultPort accountVault) {
        this.financialAuth = financialAuth;
        this.smsAuth = smsAuth;
        this.payMeansRepository = payMeansRepository;
        this.agentPayMapRepository = agentPayMapRepository;
        this.realtmAuthHistory = realtmAuthHistory;
        this.accountVault = accountVault;
    }

    /**
     * U1 본인명의 등록. 인증 성공 → 활성(10).
     */
    @Transactional
    public PayMeansResult registerByOwner(RegisterPayMeansCommand cmd) {
        PayMeansType type = PayMeansType.fromCode(cmd.payMeansTypeCd());

        // 기준3: 동일 계좌/카드는 본인·대리 무관 1건만 허용 (금고 대체ID 기준)
        Long altrnateId = accountVault.toAltrnateId(cmd.accountOrCardNo());
        if (payMeansRepository.existsActiveByAltrnateId(cmd.tenantId(), altrnateId)) {
            throw new IllegalStateException("이미 등록된 계좌/카드입니다. 동일 계좌/카드는 1건만 등록할 수 있습니다.");
        }

        // auth-first: 실시간인증 선행 (이력 INSERT→인증→결과 UPDATE)
        Long authSeq = authenticateWithHistory(cmd.tenantId(), type, cmd.fincInstCd(),
                cmd.accountOrCardNo(), cmd.holderNm(), cmd.cardValidYymm());

        // 원본 계좌/카드번호를 금고에 암호화 저장하고 대체ID 확보
        altrnateId = accountVault.store(cmd.tenantId(), cmd.accountOrCardNo(), type.code(), cmd.fincInstCd());

        PayMeans payMeans = PayMeans.registerByOwner(
                cmd.tenantId(), cmd.customerNo(), type,
                cmd.payMeansNm(), cmd.fincInstCd(),
                altrnateId, cmd.holderNm());
        payMeans = payMeansRepository.save(payMeans);

        // 인증 성공 이력에 채번된 납부수단번호 반영
        realtmAuthHistory.linkPayMeans(authSeq, cmd.tenantId(), payMeans.getPayMeansNo());

        return toResult(payMeans, null);
    }

    /**
     * U2/U9 대리인(타인명의) 등록.
     * <ul>
     *   <li>대면: 활성(10) + AgentPayMap 활성</li>
     *   <li>비대면: 등록중(01) + AgentPayMap 요청 + 소유주 동의요청 발송</li>
     * </ul>
     */
    @Transactional
    public PayMeansResult registerByAgent(RegisterAgentPayMeansCommand cmd) {
        PayMeansType type = PayMeansType.fromCode(cmd.payMeansTypeCd());
        if (!type.isAgentPayable()) {
            throw new IllegalArgumentException("간편결제는 대리납부 등록이 불가합니다.");
        }

        // 기준1: 동일 대리납부자↔피대리납부자 관계 중복(진행중/활성) 불가
        if (agentPayMapRepository.existsActiveRelation(
                cmd.tenantId(), cmd.agentPayerCustomerNo(), cmd.bnfcPayerCustomerNo())) {
            throw new IllegalStateException("이미 등록(또는 진행중)된 대리납부 관계입니다.");
        }

        // 기준3: 동일 계좌/카드는 본인·대리 무관 1건만 허용 (금고 대체ID 기준)
        Long altrnateId = accountVault.toAltrnateId(cmd.accountOrCardNo());
        if (payMeansRepository.existsActiveByAltrnateId(cmd.tenantId(), altrnateId)) {
            throw new IllegalStateException("이미 등록된 계좌/카드입니다. 동일 계좌/카드는 1건만 등록할 수 있습니다.");
        }

        // auth-first: 실시간인증 선행 (이력 INSERT→인증→결과 UPDATE)
        Long authSeq = authenticateWithHistory(cmd.tenantId(), type, cmd.fincInstCd(),
                cmd.accountOrCardNo(), cmd.holderNm(), cmd.cardValidYymm());

        // 원본 계좌/카드번호를 금고에 암호화 저장하고 대체ID 확보
        altrnateId = accountVault.store(cmd.tenantId(), cmd.accountOrCardNo(), type.code(), cmd.fincInstCd());

        AgentRegistrationType agentType = cmd.faceToFace()
                ? AgentRegistrationType.FACE_TO_FACE
                : AgentRegistrationType.NON_FACE;

        // 수단 소유주(=대리납부자) 명의로 PayMeans 생성
        PayMeans payMeans = PayMeans.registerByAgent(
                cmd.tenantId(), cmd.agentPayerCustomerNo(), type,
                cmd.payMeansNm(), cmd.fincInstCd(),
                altrnateId, cmd.holderNm(), agentType);
        payMeans = payMeansRepository.save(payMeans);

        // 인증 성공 이력에 채번된 납부수단번호 반영
        realtmAuthHistory.linkPayMeans(authSeq, cmd.tenantId(), payMeans.getPayMeansNo());

        // 대리납부 맵핑 생성 (요청)
        AgentPayMap map = AgentPayMap.request(
                cmd.tenantId(), payMeans.getPayMeansNo(),
                cmd.agentPayerCustomerNo(), cmd.bnfcPayerCustomerNo(), cmd.relCatgCd());

        if (cmd.faceToFace()) {
            // 대면: 즉시 동의 완료 처리
            map.approve();
            map = agentPayMapRepository.save(map);
        } else {
            // 비대면: 요청 상태 저장 후 소유주에게 동의요청 발송
            map = agentPayMapRepository.save(map);
            smsAuth.sendConsentRequest(new SmsAuthGatewayPort.SendCommand(
                    cmd.tenantId(), map.getAgentPayMapSeqno(),
                    cmd.ownerPhoneNo(), cmd.agentPayerCustomerNo()));
        }

        return toResult(payMeans, map.getAgentPayMapSeqno());
    }

    /**
     * 기존 납부수단 재사용(대리납부). 부모(소유주)의 기존 수단을 자식이 사용하도록 연결.
     * <ul>
     *   <li>새 PayMeans 생성하지 않음 (기존 수단 사용)</li>
     *   <li>금고에서 원본 복호화 → 매번 재인증(상태 변화 감지)</li>
     *   <li>AgentPayMap 요청(REQ) 생성 + 소유주에게 동의요청 발송 (항상 동의 필요)</li>
     * </ul>
     */
    @Transactional
    public PayMeansResult linkExistingMeansToAgent(LinkExistingMeansCommand cmd) {
        // 1. 기존 납부수단 조회 (소유주=부모 소유, 활성 상태여야 함)
        PayMeans payMeans = payMeansRepository.findById(cmd.payMeansNo())
                .orElseThrow(() -> new IllegalArgumentException(
                        "납부수단을 찾을 수 없습니다: " + cmd.payMeansNo()));
        if (payMeans.getStatus() != PayMeansStatus.ACTIVE) {
            throw new IllegalStateException("활성 상태의 납부수단만 대리납부에 연결할 수 있습니다.");
        }
        PayMeansType type = payMeans.getType();
        if (!type.isAgentPayable()) {
            throw new IllegalArgumentException("간편결제는 대리납부 등록이 불가합니다.");
        }

        // 2. 중복 체크1: 동일 대리납부자↔피대리납부자 관계 중복 불가
        if (agentPayMapRepository.existsActiveRelation(
                cmd.tenantId(), cmd.agentPayerCustomerNo(), cmd.bnfcPayerCustomerNo())) {
            throw new IllegalStateException("이미 등록(또는 진행중)된 대리납부 관계입니다.");
        }

        // 3. 금고에서 원본 계좌/카드번호 복호화
        String accountOrCardNo = accountVault.resolve(payMeans.getBankacctCardAltrnateId())
                .orElseThrow(() -> new IllegalStateException("금고에 계좌/카드 정보가 없어 재인증할 수 없습니다."));

        // 4. auth-first 재인증 (매번 수행 - 금융기관 상태 변화 감지)
        authenticateWithHistory(cmd.tenantId(), type, payMeans.getFincInstCd(),
                accountOrCardNo, payMeans.getHolderNm(), payMeans.getCardValidYymm());

        // 5. AgentPayMap 요청 생성 (기존 수단은 본인 등록분이므로 항상 소유주 동의 필요 → 비대면 취급)
        AgentPayMap map = AgentPayMap.request(
                cmd.tenantId(), payMeans.getPayMeansNo(),
                cmd.agentPayerCustomerNo(), cmd.bnfcPayerCustomerNo(), cmd.relCatgCd());
        map = agentPayMapRepository.save(map);

        // 6. 소유주(부모)에게 동의요청 발송
        smsAuth.sendConsentRequest(new SmsAuthGatewayPort.SendCommand(
                cmd.tenantId(), map.getAgentPayMapSeqno(),
                cmd.ownerPhoneNo(), cmd.agentPayerCustomerNo()));

        // 기존 수단(PayMeans)의 상태는 이미 활성(10)이므로 변경하지 않음
        return toResult(payMeans, map.getAgentPayMapSeqno());
    }

    /**
     * auth-first 실시간인증 + 이력 기록.
     * <ol>
     *   <li>인증이력 INSERT (상태 01 인증중)</li>
     *   <li>금융기관 인증 실행</li>
     *   <li>이력 UPDATE (성공 10 / 실패 40) — 실패해도 이력은 남음</li>
     *   <li>실패 시 예외 (PayMeans 저장 안 함)</li>
     * </ol>
     *
     * @return 실시간인증순번 (성공 시 이후 payMeansId 링크에 사용)
     */
    private Long authenticateWithHistory(String tenantId, PayMeansType type, String fincInstCd,
                                         String accountOrCardNo, String holderNm, String cardValidYymm) {
        // 1. 인증 요청 이력 생성 (01 인증중) — 별도 트랜잭션이 아니므로 실패 예외 시 함께 롤백됨.
        //    실패 이력을 남기기 위해 REQUIRES_NEW 로 분리한다(아래 selfInvocation 회피 위해 포트 사용).
        Long authSeq = realtmAuthHistory.createRequested(
                new RealtmAuthHistoryPort.RequestCommand(
                        tenantId, type.code(), authMethodOf(type), fincInstCd, accountOrCardNo));

        // 2. 금융기관 인증
        FinancialAuthGatewayPort.AuthResult result = financialAuth.authenticate(
                new FinancialAuthGatewayPort.AuthCommand(
                        tenantId, type.code(), fincInstCd, accountOrCardNo, holderNm, cardValidYymm));

        // 3. 결과 반영 (성공/실패 모두 이력 UPDATE)
        realtmAuthHistory.applyResult(authSeq, tenantId, result.success(),
                result.externalAuthKey(), result.resultCode(), result.resultMessage());

        // 4. 실패 시 예외 → PayMeans 저장 안 함
        if (!result.success()) {
            throw new IllegalStateException(
                    "실시간인증 실패: " + result.resultCode() + " " + result.resultMessage());
        }
        return authSeq;
    }

    /** 유형별 인증방법코드 (은행/카드 실시간인증) */
    private String authMethodOf(PayMeansType type) {
        return switch (type) {
            case BANK -> "01";      // 계좌 실명조회
            case CARD -> "02";      // 카드 유효성
            case SIMPLE_PAY -> "05";
        };
    }

    private PayMeansResult toResult(PayMeans m, Long agentPayMapSeqno) {
        return new PayMeansResult(
                m.getPayMeansNo(),
                m.getStatus().code(),
                m.getStatus().label(),
                agentPayMapSeqno);
    }
}
