package com.skt.autopay.paymeansregistration.infra.vault;

import com.skt.autopay.paymeansregistration.application.port.AccountVaultPort;
import com.skt.autopay.paymeansregistration.infra.persistence.entity.BankacctCardInfoEntity;
import com.skt.autopay.paymeansregistration.infra.persistence.mapper.BankacctCardInfoMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Optional;

/**
 * 계좌/카드 정보 금고 어댑터.
 *
 * <p>원본번호를 AES로 암호화하여 BANKACCT_CARD_INFO에 저장하고, 대체ID(원본 해시 기반 결정적 값)를 반환한다.
 * 대체ID가 결정적이므로 같은 번호는 같은 대체ID → 중복 체크와 재사용 조회가 모두 가능하다.
 *
 * <p>암호화 키는 설정값(autopay.vault.aes-key)에서 주입. 데모 수준이며 실제로는 KMS 등 키관리 필요.
 */
@Component
public class AccountVaultAdapter implements AccountVaultPort {

    private static final String ALGORITHM = "AES";

    private final BankacctCardInfoMapper mapper;
    private final SecretKeySpec keySpec;

    public AccountVaultAdapter(BankacctCardInfoMapper mapper,
                               @Value("${autopay.vault.aes-key:autopay-demo-key-1234}") String aesKey) {
        this.mapper = mapper;
        // AES-128: 키를 16바이트로 정규화
        byte[] key = normalizeKey(aesKey);
        this.keySpec = new SecretKeySpec(key, ALGORITHM);
    }

    @Override
    public Long store(String tenantId, String accountOrCardNo, String payMeansTypeCd, String fincInstCd) {
        if (accountOrCardNo == null) {
            return null;
        }
        Long altrnateId = toAltrnateId(accountOrCardNo);

        // 이미 저장돼 있으면 멱등 처리
        if (mapper.selectById(altrnateId) != null) {
            return altrnateId;
        }

        BankacctCardInfoEntity e = new BankacctCardInfoEntity();
        e.setBankacctCardAltrnateId(altrnateId);
        e.setTenantId(tenantId);
        e.setAcctCardNoEncrypt(encrypt(accountOrCardNo));
        e.setPayMeansTypeCd(payMeansTypeCd);
        e.setFincInstCd(fincInstCd);
        mapper.insert(e);
        return altrnateId;
    }

    @Override
    public Optional<String> resolve(Long bankacctCardAltrnateId) {
        if (bankacctCardAltrnateId == null) {
            return Optional.empty();
        }
        BankacctCardInfoEntity e = mapper.selectById(bankacctCardAltrnateId);
        if (e == null) {
            return Optional.empty();
        }
        return Optional.of(decrypt(e.getAcctCardNoEncrypt()));
    }

    @Override
    public Long toAltrnateId(String accountOrCardNo) {
        if (accountOrCardNo == null) {
            return null;
        }
        // 결정적: SHA-256 해시 앞 부분을 양수 Long 으로 변환
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(accountOrCardNo.getBytes(StandardCharsets.UTF_8));
            long v = 0L;
            for (int i = 0; i < 7; i++) { // 7바이트 → 양수 범위 유지
                v = (v << 8) | (digest[i] & 0xff);
            }
            return v & 0x7fffffffffffffffL;
        } catch (Exception ex) {
            throw new IllegalStateException("대체ID 생성 실패", ex);
        }
    }

    private String encrypt(String plain) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] enc = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(enc);
        } catch (Exception ex) {
            throw new IllegalStateException("암호화 실패", ex);
        }
    }

    private String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] dec = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(dec, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IllegalStateException("복호화 실패", ex);
        }
    }

    private byte[] normalizeKey(String key) {
        byte[] raw = key.getBytes(StandardCharsets.UTF_8);
        byte[] out = new byte[16];
        for (int i = 0; i < 16; i++) {
            out[i] = i < raw.length ? raw[i] : 0;
        }
        return out;
    }
}
