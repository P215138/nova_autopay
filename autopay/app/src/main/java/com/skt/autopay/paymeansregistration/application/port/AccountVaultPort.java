package com.skt.autopay.paymeansregistration.application.port;

import java.util.Optional;

/**
 * 계좌/카드 정보 금고 포트.
 *
 * <p>계좌/카드 원본번호를 암호화하여 별도 저장하고, 대체ID(토큰)만 도메인에 노출한다.
 * 대체ID는 원본번호 기반 결정적 값이라 같은 번호는 항상 같은 대체ID를 가진다(중복 판단).
 */
public interface AccountVaultPort {

    /**
     * 원본번호를 암호화 저장하고 대체ID를 반환한다.
     * 이미 같은 번호가 저장돼 있으면 기존 대체ID를 그대로 반환(멱등).
     */
    Long store(String tenantId, String accountOrCardNo, String payMeansTypeCd, String fincInstCd);

    /** 대체ID로 원본번호를 복호화하여 반환. */
    Optional<String> resolve(Long bankacctCardAltrnateId);

    /** 원본번호에 대응하는 대체ID를 계산(저장 없이). 중복 체크용. */
    Long toAltrnateId(String accountOrCardNo);
}
