package com.skt.autopay.customer.application;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.skt.autopay.customer.application.dto.CustomerView;
import com.skt.autopay.customer.application.dto.RegisterCustomerCommand;
import com.skt.autopay.customer.infra.persistence.entity.CustomerEntity;
import com.skt.autopay.customer.infra.persistence.mapper.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 고객 등록/조회 서비스.
 */
@Service
public class CustomerService {

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Transactional
    public CustomerView register(RegisterCustomerCommand cmd) {
        String customerNo = (cmd.customerNo() == null || cmd.customerNo().isBlank())
                ? generateCustomerNo()
                : cmd.customerNo();

        CustomerEntity e = new CustomerEntity();
        e.setCustomerNo(customerNo);
        String normalizedRrn = cmd.rrn() == null ? null : cmd.rrn().replace("-", "");
        e.setCustomerNm(cmd.customerNm());
        e.setRrn(normalizedRrn);
        e.setGenderCd(resolveGender(cmd.genderCd(), normalizedRrn));
        e.setMobilePhno(cmd.mobilePhno());

        customerMapper.insert(e);
        return toView(e);
    }

    public List<CustomerView> findAll() {
        return customerMapper.selectList(
                        new QueryWrapper<CustomerEntity>().orderByDesc("FIRST_REGIST_DTM"))
                .stream().map(this::toView).toList();
    }

    public CustomerView findOne(String customerNo) {
        CustomerEntity e = customerMapper.selectById(customerNo);
        if (e == null) {
            throw new IllegalArgumentException("고객을 찾을 수 없습니다: " + customerNo);
        }
        return toView(e);
    }

    /** 주민번호로 고객 조회 (하이픈 유무 무관) */
    public CustomerView findByRrn(String rrn) {
        String normalized = rrn == null ? null : rrn.replace("-", "");
        CustomerEntity e = customerMapper.selectOne(
                new QueryWrapper<CustomerEntity>().eq("RRN", normalized));
        if (e == null) {
            throw new IllegalArgumentException("해당 주민번호의 고객을 찾을 수 없습니다.");
        }
        return toView(e);
    }

    /** 성별 입력값 우선, 없으면 주민번호 뒷자리 첫 숫자로 판별 */
    private String resolveGender(String genderCd, String rrn) {
        if (genderCd != null && !genderCd.isBlank()) {
            return genderCd;
        }
        if (rrn != null) {
            String digits = rrn.replace("-", "");
            if (digits.length() >= 7) {
                char g = digits.charAt(6);
                if (g == '1' || g == '3' || g == '5' || g == '7' || g == '9') return "M";
                if (g == '2' || g == '4' || g == '6' || g == '8' || g == '0') return "F";
            }
        }
        return null;
    }

    /** 9로 시작하는 10자리 숫자 채번 (뒤 9자리 랜덤) */
    private String generateCustomerNo() {
        int rest = ThreadLocalRandom.current().nextInt(0, 1_000_000_000); // 0 ~ 999,999,999
        return "9" + String.format("%09d", rest);
    }

    private CustomerView toView(CustomerEntity e) {
        return new CustomerView(
                e.getCustomerNo(),
                e.getCustomerNm(),
                e.getRrn(),
                e.getGenderCd(),
                e.getMobilePhno());
    }
}
