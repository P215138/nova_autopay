-- ==========================================================================
-- autopay 스키마 DDL (MySQL 8.0)
-- 엔티티 기준: PAY_MEANS/PAY_ACCT는 테이블 정의서(엑셀 이미지),
--            나머지 6개는 entity-definitions.md 한글명 -> 영문 물리명 변환.
-- ⚠ 이미지 판독/명명 변환으로 컬럼명·타입·PK에 오차 가능. 원본 대조 필요.
-- Oracle 타입 매핑: NUMBER->BIGINT, NUMBER(p,s)->DECIMAL(p,s),
--                  VARCHAR2->VARCHAR, DATE->DATETIME
-- ==========================================================================

-- --------------------------------------------------------------------------
-- 1. 납부수단 (PAY_MEANS)
-- --------------------------------------------------------------------------
CREATE TABLE PAY_MEANS (
    PAY_MEANS_NO                        BIGINT        NOT NULL COMMENT '납부수단번호(PK)',
    TENANT_ID                           VARCHAR(10)   NOT NULL COMMENT '테넌트ID',
    SUB_TENANT_ID                       VARCHAR(10)            COMMENT '서브테넌트ID',
    PAY_MEANS_NM_ENCRYPT                VARCHAR(512)           COMMENT '납부수단명 암호화',
    PAY_MEANS_STAT_CD                   VARCHAR(2)             COMMENT '납부수단상태코드(01/02/10/20/30/40)',
    PAY_MEANS_CHG_REASON_CD             VARCHAR(2)    NOT NULL COMMENT '납부수단변경사유코드',
    CUSTOMER_NO                         VARCHAR(20)            COMMENT '고객번호',
    CUSTOMER_CNTCINFO_ID                BIGINT                 COMMENT '고객연락처ID',
    CNTC_PHNO_ENCRYPT                   VARCHAR(512)           COMMENT '연락처전화번호 암호화',
    PAY_MEANS_TYPE_CD                   VARCHAR(2)    NOT NULL COMMENT '납부수단유형코드(01은행/02카드/05간편결제)',
    FINC_INST_CD                        VARCHAR(3)             COMMENT '금융기관코드',
    ISSUNC_CARDCO_CD                    VARCHAR(3)             COMMENT '발급카드사코드',
    BANKACCT_CARD_ALTRNATE_ID           BIGINT                 COMMENT '계좌카드대체ID',
    CARD_VALID_YYMM                     VARCHAR(6)             COMMENT '카드유효연월',
    OVERSEAS_CARD_YN                    VARCHAR(1)             COMMENT '해외카드여부',
    BANKACCT_CARDHDR_NM_ENCRYPT         VARCHAR(512)           COMMENT '계좌카드소유자명 암호화',
    CUSTOMER_NO_IDNT_CATG_CD            VARCHAR(2)             COMMENT '고객번호식별구분코드',
    RSDT_CORP_BIZOPR_ALTRNATE_ID        VARCHAR(15)            COMMENT '주민법인사업자대체ID',
    RRN_CNFRM_YN                        VARCHAR(1)             COMMENT '주민등록번호확인여부',
    BANKACCT_CARDHDR_CUSTOMER_REL_CD    VARCHAR(3)             COMMENT '계좌카드주고객관계코드',
    AGENT_RSDT_CORP_BIZOPR_ALTRNATE_ID  VARCHAR(15)            COMMENT '대리인주민법인사업자대체ID',
    AGENT_NM_ENCRYPT                    VARCHAR(512)           COMMENT '대리인명 암호화',
    AGENT_CUSTOMER_REL_CD               VARCHAR(3)             COMMENT '대리인고객관계코드',
    CUSTOMER_ROLE_REL_CD                VARCHAR(2)             COMMENT '고객역할관계코드',
    AGENT_CNTC_PHNO_ENCRYPT             VARCHAR(512)           COMMENT '대리인연락처전화번호 암호화',
    FIRST_REGIST_DTM                    DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID                    VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM                       DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID                       VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (PAY_MEANS_NO),
    KEY IDX_PAY_MEANS_TENANT_CUST (TENANT_ID, CUSTOMER_NO),
    KEY IDX_PAY_MEANS_STAT (PAY_MEANS_STAT_CD)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='납부수단';

-- --------------------------------------------------------------------------
-- 2. 납부계정 (PAY_ACCT)
-- --------------------------------------------------------------------------
CREATE TABLE PAY_ACCT (
    PAY_ACCT_NO                     BIGINT        NOT NULL COMMENT '납부계정번호(PK)',
    TENANT_ID                       VARCHAR(10)   NOT NULL COMMENT '테넌트ID(PK)',
    SUB_TENANT_ID                   VARCHAR(10)            COMMENT '서브테넌트ID',
    PAY_ACCT_NM_ENCRYPT             VARCHAR(512)           COMMENT '납부계정명 암호화',
    PAY_ACCT_STAT_CD                VARCHAR(2)             COMMENT '납부계정상태코드',
    PAY_CATG_CD                     VARCHAR(2)    NOT NULL COMMENT '납부구분코드(PK)',
    CONTRACT_NO                     VARCHAR(12)            COMMENT '계약번호',
    INVOICE_ACCT_NO                 BIGINT                 COMMENT '청구계정번호',
    PAY_MTHD_CD                     VARCHAR(2)             COMMENT '납부방법코드',
    PAY_CYCLE_CD                    VARCHAR(2)             COMMENT '납부주기코드',
    PAY_CYCLE_DAY_CD                VARCHAR(2)             COMMENT '납부주기일코드',
    PAY_MEANS_NO                    BIGINT                 COMMENT '납부수단번호',
    RESERVE_PAY_MEANS_NO            BIGINT        NOT NULL COMMENT '예비납부수단번호(PK)',
    BILLKEY                         VARCHAR(60)            COMMENT 'BILLKEY',
    AUTO_PAY_DC_EXCLS_YN            VARCHAR(1)             COMMENT '자동납부할인제외여부',
    SEPARAT_PAY_GROUP_ID            VARCHAR(15)            COMMENT '분리납부그룹ID',
    SEPARAT_AMT                     DECIMAL(15,0)          COMMENT '분리금액',
    SEPARAT_RATIO                   DECIMAL(5,2)           COMMENT '분리비율',
    SEPARAT_PAY_REPRSNT_YN          VARCHAR(1)             COMMENT '분리납부대표여부',
    RCPTN_ORG_RID                   VARCHAR(10)            COMMENT '접수조직RID',
    PRCSSG_ORG_RID                  VARCHAR(10)            COMMENT '처리조직RID',
    DIRSTR_ORG_RID                  VARCHAR(10)            COMMENT '지시장조직RID',
    SALSTR_ORG_RID                  VARCHAR(10)            COMMENT '판매점조직RID',
    APPLREQ_PRCSSR_RID              VARCHAR(10)            COMMENT '신청자처리RID',
    PAY_CNTC_TEXTMSG_MSG_TYPE_CD    VARCHAR(1)             COMMENT '납부연락문자메시지유형코드',
    FIRST_APPLREQ_DT                VARCHAR(8)             COMMENT '최초신청일자',
    APPLREQ_DT                      VARCHAR(8)             COMMENT '신청일자',
    APPLREQ_CANCL_DT                VARCHAR(8)             COMMENT '신청취소일자',
    AUTO_PAY_APPLREQ_CATG_CD        VARCHAR(2)             COMMENT '자동납부신청구분코드',
    AUTO_PAY_CHG_REASON_CD          VARCHAR(2)             COMMENT '자동납부변경사유코드',
    AUTO_PAY_RCPTN_CATG_CD          VARCHAR(2)             COMMENT '자동납부접수구분코드',
    AUTO_PAY_RCPTN_CHNL_CATG_CD     VARCHAR(2)             COMMENT '자동납부접수채널구분코드',
    PAY_ACCT_AUTHENTC_REQ_DT        VARCHAR(8)             COMMENT '납부계정인증요청일자',
    AUTO_PAY_AUTHENTC_RESULT_CD     VARCHAR(2)             COMMENT '자동납부인증결과코드',
    CMS_YN                          VARCHAR(1)             COMMENT 'CMS여부',
    FB_YN                           VARCHAR(1)             COMMENT 'FB여부',
    EDI_YN                          VARCHAR(1)             COMMENT 'EDI여부',
    CMS_FB_REQ_DT                   VARCHAR(8)             COMMENT 'CMSFB요청일자',
    EDI_AUTO_PAY_RCPTN_CATG_CD      VARCHAR(2)             COMMENT 'EDI자동납부접수구분코드',
    EDI_AUTHENTC_REQ_DT             VARCHAR(8)             COMMENT 'EDI인증요청일자',
    EDI_AUTO_PAY_AUTHENTC_RESULT_CD VARCHAR(4)             COMMENT 'EDI자동납부인증결과코드',
    EDI_APPLY_DT                    DATETIME               COMMENT 'EDI적용일자',
    EDI_AUTO_PAY_APPLREQ_DTM        VARCHAR(3)             COMMENT 'EDI자동납부신청일시',
    FIRST_WDRW_PLAN_DT              VARCHAR(8)             COMMENT '최초인출계획일자',
    FIRST_WDRW_DT                   VARCHAR(8)             COMMENT '최초인출일자',
    CURR8TH_WDRW_YN                 VARCHAR(1)             COMMENT '당월차인출여부',
    WDRW_TN                         BIGINT                 COMMENT '인출TN',
    FINAL_PAY_ACCT_HIST_SEQNO       BIGINT                 COMMENT '최종납부계정이력순번',
    PAYER_NO_CATG_CD                VARCHAR(2)             COMMENT '납부자번호구분코드',
    APPLICHKR_BARCD_NO              VARCHAR(1)             COMMENT '신청서확인바코드번호',
    WDRW_AGREE_EVIDENCE_TYPE_CD     VARCHAR(1)             COMMENT '인출동의증거유형코드',
    WDRW_AGREE_EVIDENCE_KEY_ID      VARCHAR(1)             COMMENT '인출동의증거키ID',
    EVIDENCE_IMAGE_KEY_API_Y_CATG_CD VARCHAR(2)            COMMENT '증빙이미지키API구분코드',
    FIRST_REGIST_DTM                DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID                VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM                   DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID                   VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (PAY_ACCT_NO, TENANT_ID, PAY_CATG_CD, RESERVE_PAY_MEANS_NO),
    KEY IDX_PAY_ACCT_INVOICE (INVOICE_ACCT_NO),
    KEY IDX_PAY_ACCT_CONTRACT (CONTRACT_NO),
    KEY IDX_PAY_ACCT_MEANS (PAY_MEANS_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='납부계정';

-- --------------------------------------------------------------------------
-- 3. 납부계정접수 (PAY_ACCT_RCPTN)
-- --------------------------------------------------------------------------
CREATE TABLE PAY_ACCT_RCPTN (
    PAY_ACCT_NO                 BIGINT        NOT NULL COMMENT '납부계정번호(PK)',
    PAY_MEANS_SEQ               BIGINT        NOT NULL COMMENT '납부수단순번(PK)',
    TENANT_ID                   VARCHAR(10)   NOT NULL COMMENT '테넌트ID(PK)',
    AUTO_PAY_REGIST_SEQ         BIGINT        NOT NULL COMMENT '자동납부등록순번(PK)',
    PAY_MTHD_CD                 VARCHAR(2)             COMMENT '납부방법코드',
    PAY_ACCT_STAT_CD            VARCHAR(2)             COMMENT '납부계정상태코드',
    PAY_AMT_TYPE_CD             VARCHAR(2)             COMMENT '납부금액유형코드',
    PAY_CYCLE_CD                VARCHAR(2)             COMMENT '납부주기코드',
    PAY_CYCLE_DAY_CD            VARCHAR(2)             COMMENT '납부주기일코드',
    AUTO_PAY_REGIST_DT          VARCHAR(8)             COMMENT '자동납부등록일자',
    AUTO_PAY_TERMINATE_DT       VARCHAR(8)             COMMENT '자동납부해지일자',
    AUTO_PAY_REGIST_CHNL_CD     VARCHAR(2)             COMMENT '자동납부등록채널코드',
    AUTO_PAY_TERMINATE_CHNL_CD  VARCHAR(2)             COMMENT '자동납부해지채널코드',
    PAY_MEANS_CHG_DT            VARCHAR(8)             COMMENT '납부수단변경일자',
    CHG_CHNL_CD                 VARCHAR(2)             COMMENT '변경채널코드',
    PAY_INST_CD                 VARCHAR(2)             COMMENT '납부기관코드',
    PAY_INST_ACCT_CD            VARCHAR(20)            COMMENT '납부기관계좌코드',
    BANKACCT_CARD_ALTRNATE_ID   BIGINT                 COMMENT '계좌카드대체ID',
    AUTHENTC_MTHD_CD            VARCHAR(2)             COMMENT '인증방법코드',
    EDI_SVC_CD                  VARCHAR(10)            COMMENT 'EDI서비스코드',
    FB_SVC_CD                   VARCHAR(10)            COMMENT 'FB서비스코드',
    WDRW_TYPE_DTL_CD            VARCHAR(20)            COMMENT '인출유형상세코드',
    PAY_AGENT_YN                VARCHAR(1)             COMMENT '납부대리인여부',
    AGENT_PAY_ACCT_NO           BIGINT                 COMMENT '대리납부계정번호',
    PAY_AMT                     DECIMAL(15,0)          COMMENT '납부금액',
    CMS_CD                      VARCHAR(10)            COMMENT 'CMS코드',
    APPLREQ_INST_CD             VARCHAR(10)            COMMENT '신청기관코드',
    PRCSSG_CHNL_CD              VARCHAR(2)             COMMENT '처리채널코드',
    PRCSSR_NO                   VARCHAR(20)            COMMENT '처리자번호',
    PRCSSG_DT                   VARCHAR(8)             COMMENT '처리일자',
    PRCSSG_RESULT_CD            VARCHAR(2)             COMMENT '처리결과코드',
    PRCSSG_RESULT_MSG           VARCHAR(500)           COMMENT '처리결과메시지',
    PREV_PAY_MTHD_CD            VARCHAR(2)             COMMENT '이전납부방법코드',
    PREV_PAY_MEANS_ID           BIGINT                 COMMENT '이전납부수단ID',
    PREV_PAY_INST_CD            VARCHAR(2)             COMMENT '이전납부기관코드',
    PREV_PAY_INST_ACCT_CD       VARCHAR(20)            COMMENT '이전납부기관계좌코드',
    REQ_REASON                  VARCHAR(200)           COMMENT '요청사유',
    FIRST_REGIST_DTM            DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID            VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM               DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID               VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (PAY_ACCT_NO, PAY_MEANS_SEQ, TENANT_ID, AUTO_PAY_REGIST_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='납부계정접수';

-- --------------------------------------------------------------------------
-- 4. 납부계정인증 (PAY_ACCT_AUTHENTC)
-- --------------------------------------------------------------------------
CREATE TABLE PAY_ACCT_AUTHENTC (
    PAY_ACCT_NO                     BIGINT        NOT NULL COMMENT '납부계정번호(PK)',
    PAY_MEANS_SEQ                   BIGINT        NOT NULL COMMENT '납부수단순번(PK)',
    TENANT_ID                       VARCHAR(10)   NOT NULL COMMENT '테넌트ID(PK)',
    AUTHENTC_SEQ                    BIGINT        NOT NULL COMMENT '인증순번(PK)',
    PAY_MTHD_CD                     VARCHAR(2)             COMMENT '납부방법코드',
    AUTHENTC_MTHD_CD                VARCHAR(2)             COMMENT '인증방법코드',
    AUTHENTC_STAT_CD                VARCHAR(2)             COMMENT '인증상태코드',
    AUTHENTC_REQ_DTM                DATETIME               COMMENT '인증요청일시',
    AUTHENTC_CMPL_DTM               DATETIME               COMMENT '인증완료일시',
    AUTHENTC_EXP_DTM                DATETIME               COMMENT '인증만료일시',
    AUTHENTC_CHNL_CD                VARCHAR(2)             COMMENT '인증채널코드',
    AUTHENTC_INST_CD                VARCHAR(2)             COMMENT '인증기관코드',
    AUTHENTC_RESULT_CD              VARCHAR(2)             COMMENT '인증결과코드',
    AUTHENTC_RESULT_MSG             VARCHAR(500)           COMMENT '인증결과메시지',
    EXTNL_AUTHENTC_KEY              VARCHAR(100)           COMMENT '외부인증키',
    EXTNL_AUTHENTC_RESULT_CD        VARCHAR(4)             COMMENT '외부인증결과코드',
    EXTNL_AUTHENTC_RESULT_MSG       VARCHAR(500)           COMMENT '외부인증결과메시지',
    CARD_APPRV_NO                   VARCHAR(20)            COMMENT '카드승인번호',
    CARD_APPRV_DTM                  DATETIME               COMMENT '카드승인일시',
    CARD_APPRV_AMT                  DECIMAL(15,0)          COMMENT '카드승인금액',
    CARD_INSTLMT_MONTH              INT                    COMMENT '카드할부개월',
    CARD_NO_INTRST_YN               VARCHAR(1)             COMMENT '카드무이자여부',
    CMS_AUTHENTC_NO                 VARCHAR(20)            COMMENT 'CMS인증번호',
    CMS_AUTHENTC_DTM                DATETIME               COMMENT 'CMS인증일시',
    EDI_AUTHENTC_NO                 VARCHAR(20)            COMMENT 'EDI인증번호',
    EDI_AUTHENTC_DTM                DATETIME               COMMENT 'EDI인증일시',
    EDI_AUTHENTC_RESULT_CD          VARCHAR(4)             COMMENT 'EDI인증결과코드',
    EDI_AUTHENTC_RESULT_MSG         VARCHAR(500)           COMMENT 'EDI인증결과메시지',
    FB_AUTHENTC_NO                  VARCHAR(20)            COMMENT 'FB인증번호',
    FB_AUTHENTC_DTM                 DATETIME               COMMENT 'FB인증일시',
    FB_AUTHENTC_RESULT_CD           VARCHAR(4)             COMMENT 'FB인증결과코드',
    FB_AUTHENTC_RESULT_MSG          VARCHAR(500)           COMMENT 'FB인증결과메시지',
    SIMPLE_PAY_AUTHENTC_NO          VARCHAR(20)            COMMENT '간편결제인증번호',
    SIMPLE_PAY_AUTHENTC_DTM         DATETIME               COMMENT '간편결제인증일시',
    SIMPLE_PAY_AUTHENTC_RESULT_CD   VARCHAR(4)             COMMENT '간편결제인증결과코드',
    SIMPLE_PAY_AUTHENTC_RESULT_MSG  VARCHAR(500)           COMMENT '간편결제인증결과메시지',
    CMS_USE_STORE_CD                VARCHAR(10)            COMMENT 'CMS이용점코드',
    CMS_USE_STORE_NM                VARCHAR(100)           COMMENT 'CMS이용점명',
    AGENT_PAY_YN                    VARCHAR(1)             COMMENT '대리인납부여부',
    AGENT_CUSTOMER_NO               VARCHAR(20)            COMMENT '대리인고객번호',
    AGENT_AUTHENTC_NO               VARCHAR(20)            COMMENT '대리인인증번호',
    AGENT_AUTHENTC_DTM              DATETIME               COMMENT '대리인인증일시',
    PRCSSG_CHNL_CD                  VARCHAR(2)             COMMENT '처리채널코드',
    PRCSSR_NO                       VARCHAR(20)            COMMENT '처리자번호',
    PRCSSG_DT                       VARCHAR(8)             COMMENT '처리일자',
    PREV_AUTHENTC_SEQ               BIGINT                 COMMENT '이전인증순번',
    RE_AUTHENTC_YN                  VARCHAR(1)             COMMENT '재인증여부',
    FIRST_REGIST_DTM                DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID                VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM                   DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID                   VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (PAY_ACCT_NO, PAY_MEANS_SEQ, TENANT_ID, AUTHENTC_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='납부계정인증';

-- --------------------------------------------------------------------------
-- 5. 납부계정인증(WTT) (PAY_ACCT_AUTHENTC_WTT)
-- --------------------------------------------------------------------------
CREATE TABLE PAY_ACCT_AUTHENTC_WTT (
    HSHLD               VARCHAR(20)   NOT NULL COMMENT '세대(PK)',
    PAY_ACCT_NO         BIGINT        NOT NULL COMMENT '납부계정번호(PK)',
    PAY_MEANS_SEQ       BIGINT        NOT NULL COMMENT '납부수단순번(PK)',
    TENANT_ID           VARCHAR(10)   NOT NULL COMMENT '테넌트ID(PK)',
    PAY_MTHD_CD         VARCHAR(2)             COMMENT '납부방법코드',
    AUTHENTC_MTHD_CD    VARCHAR(2)             COMMENT '인증방법코드',
    AUTHENTC_STAT_CD    VARCHAR(2)             COMMENT '인증상태코드',
    NGM_CNTNT_CD        VARCHAR(2)             COMMENT 'NGM내용코드',
    START_DTM           DATETIME               COMMENT '시작일시',
    END_DTM             DATETIME               COMMENT '종료일시',
    FIRST_REGIST_DTM    DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID    VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM       DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID       VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (HSHLD, PAY_ACCT_NO, PAY_MEANS_SEQ, TENANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='납부계정인증(WTT)';

-- --------------------------------------------------------------------------
-- 6. 납부수단실시간인증 (PAY_MEANS_REALTM_AUTHENTC)
-- --------------------------------------------------------------------------
CREATE TABLE PAY_MEANS_REALTM_AUTHENTC (
    REALTM_AUTHENTC_SEQ             BIGINT        NOT NULL COMMENT '실시간인증순번(PK)',
    TENANT_ID                       VARCHAR(10)   NOT NULL COMMENT '테넌트ID(PK)',
    PAY_MTHD_CD                     VARCHAR(2)             COMMENT '납부방법코드',
    AUTHENTC_MTHD_CD                VARCHAR(2)             COMMENT '인증방법코드',
    AUTHENTC_STAT_CD                VARCHAR(2)             COMMENT '인증상태코드',
    AUTHENTC_REQ_DTM                DATETIME               COMMENT '인증요청일시',
    AUTHENTC_CMPL_DTM               DATETIME               COMMENT '인증완료일시',
    AUTHENTC_EXP_DTM                DATETIME               COMMENT '인증만료일시',
    AUTHENTC_CHNL_CD                VARCHAR(2)             COMMENT '인증채널코드',
    AUTHENTC_INST_CD                VARCHAR(2)             COMMENT '인증기관코드',
    AUTHENTC_RESULT_CD              VARCHAR(2)             COMMENT '인증결과코드',
    AUTHENTC_RESULT_MSG             VARCHAR(500)           COMMENT '인증결과메시지',
    AID                             VARCHAR(50)            COMMENT 'AID',
    EXTNL_AUTHENTC_KEY              VARCHAR(100)           COMMENT '외부인증키',
    EXTNL_AUTHENTC_RESULT_CD        VARCHAR(4)             COMMENT '외부인증결과코드',
    EXTNL_AUTHENTC_RESULT_MSG       VARCHAR(500)           COMMENT '외부인증결과메시지',
    CARD_APPRV_NO                   VARCHAR(20)            COMMENT '카드승인번호',
    CARD_APPRV_DTM                  DATETIME               COMMENT '카드승인일시',
    CARD_APPRV_AMT                  DECIMAL(15,0)          COMMENT '카드승인금액',
    PAY_MEANS_STAT_CHG_CD           VARCHAR(2)             COMMENT '납부수단상태변경코드',
    PAY_INST_CD                     VARCHAR(3)             COMMENT '납부기관코드',
    PAY_INST_ACCT_CD                VARCHAR(30)            COMMENT '납부기관계좌코드',
    BANK_ACCT_CD                    VARCHAR(30)            COMMENT '은행계좌코드',
    EDI_AUTHENTC_NO                 VARCHAR(20)            COMMENT 'EDI인증번호',
    EDI_AUTHENTC_DTM                DATETIME               COMMENT 'EDI인증일시',
    EDI_AUTHENTC_RESULT_CD          VARCHAR(4)             COMMENT 'EDI인증결과코드',
    EDI_AUTHENTC_RESULT_MSG         VARCHAR(500)           COMMENT 'EDI인증결과메시지',
    FB_AUTHENTC_NO                  VARCHAR(20)            COMMENT 'FB인증번호',
    FB_AUTHENTC_DTM                 DATETIME               COMMENT 'FB인증일시',
    FB_AUTHENTC_RESULT_CD           VARCHAR(4)             COMMENT 'FB인증결과코드',
    FB_AUTHENTC_RESULT_MSG          VARCHAR(500)           COMMENT 'FB인증결과메시지',
    CMS_AUTHENTC_NO                 VARCHAR(20)            COMMENT 'CMS인증번호',
    CMS_AUTHENTC_DTM                DATETIME               COMMENT 'CMS인증일시',
    CMS_AUTHENTC_RESULT_CD          VARCHAR(4)             COMMENT 'CMS인증결과코드',
    CMS_USE_STORE_CD                VARCHAR(10)            COMMENT 'CMS이용점코드',
    CMS_USE_STORE_NM                VARCHAR(100)           COMMENT 'CMS이용점명',
    SIMPLE_PAY_AUTHENTC_NO          VARCHAR(20)            COMMENT '간편결제인증번호',
    SIMPLE_PAY_AUTHENTC_DTM         DATETIME               COMMENT '간편결제인증일시',
    SIMPLE_PAY_AUTHENTC_RESULT_CD   VARCHAR(4)             COMMENT '간편결제인증결과코드',
    EDI_PAY_MEANS_CHG_CD            VARCHAR(2)             COMMENT 'EDI납부수단변경코드',
    EDI_PAY_MEANS_CHG_RESULT_CD     VARCHAR(4)             COMMENT 'EDI납부수단변경결과코드',
    CARD_NO_ALTRNATE_ID             BIGINT                 COMMENT '카드번호대체ID',
    CARD_VALID_YYMM                 VARCHAR(6)             COMMENT '카드유효기간',
    PRCSSG_CHNL_CD                  VARCHAR(2)             COMMENT '처리채널코드',
    PRCSSR_NO                       VARCHAR(20)            COMMENT '처리자번호',
    PRCSSG_DT                       VARCHAR(8)             COMMENT '처리일자',
    PAY_MEANS_ID                    BIGINT                 COMMENT '납부수단ID(인증 성공 후 채움)',
    FIRST_REGIST_DTM                DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID                VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM                   DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID                   VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (REALTM_AUTHENTC_SEQ, TENANT_ID),
    KEY IDX_REALTM_AUTH_MEANS (PAY_MEANS_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='납부수단실시간인증';

-- --------------------------------------------------------------------------
-- 7. 외부기관자동납부신청 (EXTERNAL_INST_AUTO_PAY_APPREQ)
-- --------------------------------------------------------------------------
CREATE TABLE EXTERNAL_INST_AUTO_PAY_APPREQ (
    EXTNL_INST_CD                   VARCHAR(10)   NOT NULL COMMENT '외부기관코드(PK)',
    AUTO_PAY_APPREQ_SEQ             BIGINT        NOT NULL COMMENT '자동납부신청순번(PK)',
    TENANT_ID                       VARCHAR(10)   NOT NULL COMMENT '테넌트ID(PK)',
    PAY_MTHD_CD                     VARCHAR(2)             COMMENT '납부방법코드',
    AUTHENTC_MTHD_CD                VARCHAR(2)             COMMENT '인증방법코드',
    AUTHENTC_STAT_CD                VARCHAR(2)             COMMENT '인증상태코드',
    EXTNL_INST_NM                   VARCHAR(100)           COMMENT '외부기관명',
    EXTNL_INST_PRCSSG_DT            VARCHAR(8)             COMMENT '외부기관처리일자',
    EXTNL_INST_PRCSSG_RESULT_CD     VARCHAR(4)             COMMENT '외부기관처리결과코드',
    EXTNL_INST_PRCSSG_RESULT_MSG    VARCHAR(500)           COMMENT '외부기관처리결과메시지',
    EXTNL_AUTHENTC_KEY              VARCHAR(100)           COMMENT '외부인증키',
    EXTNL_AUTHENTC_RESULT_CD        VARCHAR(4)             COMMENT '외부인증결과코드',
    EXTNL_AUTHENTC_RESULT_MSG       VARCHAR(500)           COMMENT '외부인증결과메시지',
    PAY_INST_CD                     VARCHAR(2)             COMMENT '납부기관코드',
    PAY_INST_ACCT_CD                VARCHAR(20)            COMMENT '납부기관계좌코드',
    PAY_INST_CARD_CD                VARCHAR(20)            COMMENT '납부기관카드코드',
    PAY_INST_SIMPLE_PAY_CD          VARCHAR(20)            COMMENT '납부기관간편결제코드',
    BANKACCT_CARD_ALTRNATE_ID       BIGINT                 COMMENT '계좌카드대체ID',
    EDI_SVC_CD                      VARCHAR(10)            COMMENT 'EDI서비스코드',
    EDI_AUTHENTC_NO                 VARCHAR(20)            COMMENT 'EDI인증번호',
    EDI_AUTHENTC_RESULT_CD          VARCHAR(4)             COMMENT 'EDI인증결과코드',
    EDI_AUTHENTC_RESULT_MSG         VARCHAR(500)           COMMENT 'EDI인증결과메시지',
    FB_SVC_CD                       VARCHAR(10)            COMMENT 'FB서비스코드',
    FB_AUTHENTC_NO                  VARCHAR(20)            COMMENT 'FB인증번호',
    FB_AUTHENTC_RESULT_CD           VARCHAR(4)             COMMENT 'FB인증결과코드',
    FB_AUTHENTC_RESULT_MSG          VARCHAR(500)           COMMENT 'FB인증결과메시지',
    SIMPLE_PAY_AUTHENTC_NO          VARCHAR(20)            COMMENT '간편결제인증번호',
    SIMPLE_PAY_AUTHENTC_RESULT_CD   VARCHAR(4)             COMMENT '간편결제인증결과코드',
    CARD_APPRV_NO                   VARCHAR(20)            COMMENT '카드승인번호',
    CARD_APPRV_AMT                  DECIMAL(15,0)          COMMENT '카드승인금액',
    PRCSSG_CHNL_CD                  VARCHAR(2)             COMMENT '처리채널코드',
    PRCSSR_NO                       VARCHAR(20)            COMMENT '처리자번호',
    PRCSSG_DT                       VARCHAR(8)             COMMENT '처리일자',
    PAY_ACCT_NO                     BIGINT                 COMMENT '납부계정번호',
    PAY_MEANS_ID                    BIGINT                 COMMENT '납부수단ID',
    AUTO_PAY_REGIST_DT              VARCHAR(8)             COMMENT '자동납부등록일자',
    APPREQ_CATG_CD                  VARCHAR(2)             COMMENT '신청구분코드',
    PRCSSG_INST_CD                  VARCHAR(10)            COMMENT '처리기관코드',
    PRCSSG_INST_NM                  VARCHAR(100)           COMMENT '처리기관명',
    EXTNL_SYS_LINK_CD               VARCHAR(10)            COMMENT '외부시스템연동코드',
    EXTNL_SYS_LINK_RESULT_CD        VARCHAR(4)             COMMENT '외부시스템연동결과코드',
    RE_PRCSSG_YN                    VARCHAR(1)             COMMENT '재처리여부',
    RE_PRCSSG_CNT                   INT                    COMMENT '재처리횟수',
    REQ_REASON                      VARCHAR(200)           COMMENT '요청사유',
    FIRST_REGIST_DTM                DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID                VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM                   DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID                   VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (EXTNL_INST_CD, AUTO_PAY_APPREQ_SEQ, TENANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='외부기관자동납부신청';

-- --------------------------------------------------------------------------
-- 8. 외부기관자동납부신청상세 (EXTERNAL_INST_AUTO_PAY_APPREQ_DTL)
-- --------------------------------------------------------------------------
CREATE TABLE EXTERNAL_INST_AUTO_PAY_APPREQ_DTL (
    EXTNL_INST_CD           VARCHAR(10)   NOT NULL COMMENT '외부기관코드(FK,PK)',
    AUTO_PAY_APPREQ_SEQ     BIGINT        NOT NULL COMMENT '자동납부신청순번(FK,PK)',
    TENANT_ID               VARCHAR(10)   NOT NULL COMMENT '테넌트ID(PK)',
    DTL_SEQ                 BIGINT        NOT NULL COMMENT '상세순번(PK)',
    PAY_MTHD_CD             VARCHAR(2)             COMMENT '납부방법코드',
    DTL_PRCSSG_RESULT       VARCHAR(500)           COMMENT '상세처리결과',
    FIRST_REGIST_DTM        DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID        VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM           DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID           VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (EXTNL_INST_CD, AUTO_PAY_APPREQ_SEQ, TENANT_ID, DTL_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='외부기관자동납부신청상세';

-- --------------------------------------------------------------------------
-- 9. 대리납부 맵핑 (PAY_AGENT_MAP)
-- --------------------------------------------------------------------------
CREATE TABLE PAY_AGENT_MAP (
    AGENT_PAY_MAP_SEQNO     BIGINT        NOT NULL COMMENT '대리납부맵일련번호(PK)',
    TENANT_ID               VARCHAR(10)   NOT NULL COMMENT '테넌트ID',
    AGENT_PAYER_CUSTOMER_NO VARCHAR(20)   NOT NULL COMMENT '대리납부자 고객번호(수단 소유주)',
    BNFC_PAYER_CUSTOMER_NO  VARCHAR(20)   NOT NULL COMMENT '피대리납부자 고객번호(수단 사용자)',
    PAY_MEANS_NO            BIGINT        NOT NULL COMMENT '납부수단번호(FK)',
    REL_CATG_CD             VARCHAR(2)             COMMENT '관계구분코드(부모/배우자/자녀)',
    AGENT_PAY_MAP_STAT_CD   VARCHAR(2)             COMMENT '상태코드(10요청/20활성/30거절/40중단/50종료)',
    VALID_START_DTM         DATETIME               COMMENT '유효시작일시',
    VALID_END_DTM           DATETIME               COMMENT '유효종료일시(soft delete)',
    FIRST_REGIST_DTM        DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID        VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM           DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID           VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (AGENT_PAY_MAP_SEQNO),
    KEY IDX_AGENT_PAY_MAP_MEANS (PAY_MEANS_NO),
    KEY IDX_AGENT_PAY_MAP_AGENT (TENANT_ID, AGENT_PAYER_CUSTOMER_NO),
    KEY IDX_AGENT_PAY_MAP_BNFC (TENANT_ID, BNFC_PAYER_CUSTOMER_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='대리납부 맵핑';

-- --------------------------------------------------------------------------
-- 10. 고객 (CUSTOMER)
-- --------------------------------------------------------------------------
CREATE TABLE CUSTOMER (
    CUSTOMER_NO         VARCHAR(20)   NOT NULL COMMENT '고객번호(PK)',
    CUSTOMER_NM         VARCHAR(100)  NOT NULL COMMENT '이름',
    RRN                 VARCHAR(14)            COMMENT '주민번호',
    GENDER_CD           VARCHAR(1)             COMMENT '성별(M 남/F 여)',
    MOBILE_PHNO         VARCHAR(20)            COMMENT '이동전화번호',
    FIRST_REGIST_DTM    DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID    VARCHAR(20)            COMMENT '최초등록자ID',
    FINAL_CHG_DTM       DATETIME               COMMENT '최종변경일시',
    FINAL_CHGR_ID       VARCHAR(20)            COMMENT '최종변경자ID',
    PRIMARY KEY (CUSTOMER_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='고객';

-- --------------------------------------------------------------------------
-- 11. 청구계정 (BILLING_ACCOUNT)
-- --------------------------------------------------------------------------
CREATE TABLE BILLING_ACCOUNT (
    BILLING_ACCOUNT_NO  VARCHAR(20)   NOT NULL COMMENT '청구계정번호(PK)',
    TENANT_ID           VARCHAR(10)   NOT NULL COMMENT '테넌트ID',
    CUSTOMER_NO         VARCHAR(20)            COMMENT '고객번호',
    PAY_CL_CODE         VARCHAR(2)             COMMENT '납부구분코드',
    BILLING_CYCLE_CODE  VARCHAR(5)             COMMENT '청구주기코드',
    USE_YN              VARCHAR(1)    NOT NULL COMMENT '사용여부',
    FIRST_REGIST_DTM    DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID    VARCHAR(20)            COMMENT '최초등록자ID',
    FINAL_CHG_DTM       DATETIME               COMMENT '최종변경일시',
    FINAL_CHGR_ID       VARCHAR(20)            COMMENT '최종변경자ID',
    PRIMARY KEY (BILLING_ACCOUNT_NO),
    KEY IDX_BILLING_ACCOUNT_CUST (TENANT_ID, CUSTOMER_NO)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='청구계정';

-- --------------------------------------------------------------------------
-- 12. 계좌카드정보 금고 (BANKACCT_CARD_INFO)
--    계좌/카드 원본번호를 암호화하여 별도 보관. PAY_MEANS에는 대체ID만 저장.
-- --------------------------------------------------------------------------
CREATE TABLE BANKACCT_CARD_INFO (
    BANKACCT_CARD_ALTRNATE_ID   BIGINT        NOT NULL COMMENT '계좌카드대체ID(PK)',
    TENANT_ID                   VARCHAR(10)   NOT NULL COMMENT '테넌트ID',
    ACCT_CARD_NO_ENCRYPT        VARCHAR(512)  NOT NULL COMMENT '계좌/카드번호 암호화(AES)',
    PAY_MEANS_TYPE_CD           VARCHAR(2)             COMMENT '납부수단유형코드(01은행/02카드/05간편결제)',
    FINC_INST_CD                VARCHAR(3)             COMMENT '금융기관/카드사 코드',
    FIRST_REGIST_DTM            DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID            VARCHAR(20)            COMMENT '최초등록자ID',
    FINAL_CHG_DTM               DATETIME               COMMENT '최종변경일시',
    FINAL_CHGR_ID               VARCHAR(20)            COMMENT '최종변경자ID',
    PRIMARY KEY (BANKACCT_CARD_ALTRNATE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='계좌카드정보 금고';

-- --------------------------------------------------------------------------
-- 13. 서비스 (SERVICE) — 이동전화 서비스
--    현행 클리모델 엔티티정보 기준 (기준일시 2026-08-27).
--    Oracle 타입 매핑: NUMBER->BIGINT, VARCHAR2->VARCHAR, DATE->DATETIME
--    · SERVICE_MGMT_NO : 1로 시작하는 10자리 자동채번(애플리케이션 채번)
--    · BILLING_ACCOUNT_NO : 청구계정번호 (BILLING_ACCOUNT 와 연동)
--    · SERVICE_STAT_CD : AC 사용중 / TG 해지 / SP 정지
-- --------------------------------------------------------------------------
CREATE TABLE SERVICE (
    SERVICE_MGMT_NO         VARCHAR(10)   NOT NULL COMMENT '서비스관리번호(PK, 1로 시작 10자리)',
    TENANT_ID               VARCHAR(10)   NOT NULL COMMENT '테넌트ID',
    BILLING_ACCOUNT_NO      VARCHAR(20)   NOT NULL COMMENT '청구계정번호(BILLING_ACCOUNT 연동)',
    SERVICE_NO_ALTRNATE_ID  VARCHAR(128)  NOT NULL COMMENT '서비스번호대체ID',
    SERVICE_STAT_CD         VARCHAR(2)             COMMENT '서비스상태코드(AC 사용중/TG 해지/SP 정지)',
    FIRST_REGIST_DTM        DATETIME      NOT NULL COMMENT '최초등록일시',
    FIRST_REGISTR_ID        VARCHAR(20)   NOT NULL COMMENT '최초등록자ID',
    FINAL_CHG_DTM           DATETIME      NOT NULL COMMENT '최종변경일시',
    FINAL_CHGR_ID           VARCHAR(20)   NOT NULL COMMENT '최종변경자ID',
    PRIMARY KEY (SERVICE_MGMT_NO),
    KEY IDX_SERVICE_BILLING (BILLING_ACCOUNT_NO),
    KEY IDX_SERVICE_TENANT (TENANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='서비스(이동전화)';
