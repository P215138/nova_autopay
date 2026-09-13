package com.nova.domain.billing.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 계약
 * - 청구계정에 연결된 서비스 계약
 */
@Entity
@Table(name = "contract")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract {

    @Id
    @Column(name = "contract_no", length = 20, nullable = false)
    private String contractNo; // #계약번호 (PK)

    @Column(name = "tenant_id", length = 10, nullable = false)
    private String tenantId; // 테넌트ID

    @Column(name = "billing_account_no", length = 20, nullable = false)
    private String billingAccountNo; // 청구계정번호 (FK)

    @Column(name = "contract_status_code", length = 5)
    private String contractStatusCode; // 계약상태코드

    @Column(name = "service_type_code", length = 10)
    private String serviceTypeCode; // 서비스유형코드

    @Builder
    public Contract(String contractNo, String tenantId,
                    String billingAccountNo, String contractStatusCode,
                    String serviceTypeCode) {
        this.contractNo = contractNo;
        this.tenantId = tenantId;
        this.billingAccountNo = billingAccountNo;
        this.contractStatusCode = contractStatusCode;
        this.serviceTypeCode = serviceTypeCode;
    }
}
