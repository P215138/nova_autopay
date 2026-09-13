package com.nova.domain.billing.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 청구계정
 * - 고객의 청구 단위 계정
 * - 1개의 청구계정에 N개의 납부계정 매핑 가능 (TOBE 구조)
 */
@Entity
@Table(name = "billing_account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BillingAccount {

    @Id
    @Column(name = "billing_account_no", length = 20, nullable = false)
    private String billingAccountNo; // #청구계정번호 (PK)

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId; // 테넌트ID (멀티테넌트)

    @Column(name = "customer_no", length = 20)
    private String customerNo; // 고객번호

    @Column(name = "pay_cl_code", length = 2)
    private String payClCode; // 납부구분코드

    @Column(name = "billing_cycle_code", length = 5)
    private String billingCycleCode; // 청구주기코드

    @Column(name = "use_yn", length = 1, nullable = false)
    private String useYn = "Y"; // 사용여부

    @Builder
    public BillingAccount(String billingAccountNo, String tenantId,
                          String customerNo, String billingCycleCode) {
        this.billingAccountNo = billingAccountNo;
        this.tenantId = tenantId;
        this.customerNo = customerNo;
        this.billingCycleCode = billingCycleCode;
    }
}
