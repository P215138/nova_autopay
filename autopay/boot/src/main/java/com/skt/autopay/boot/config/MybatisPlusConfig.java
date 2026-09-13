package com.skt.autopay.boot.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 설정
 *
 * <p>모든 모듈의 Mapper 인터페이스를 스캔하고, 페이지네이션 인터셉터와
 * 공통 감사 필드(등록/변경 일시·자) 자동 채움 핸들러를 등록한다.
 */
@Configuration
@MapperScan("com.skt.autopay.**.infra.persistence.mapper")
public class MybatisPlusConfig {

    /** 페이지네이션 등 내부 인터셉터 */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /** 공통 필드 자동 채움 (FIRST_REGIST_DTM / FINAL_CHG_DTM 등) */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            // 모든 등록/변경자 ID 는 P215138 로 고정 (요청사항)
            private static final String DEFAULT_USER_ID = "P215138";

            @Override
            public void insertFill(MetaObject metaObject) {
                LocalDateTime now = LocalDateTime.now();
                strictInsertFill(metaObject, "firstRegistDtm", LocalDateTime.class, now);
                strictInsertFill(metaObject, "finalChgDtm", LocalDateTime.class, now);
                strictInsertFill(metaObject, "firstRegistrId", String.class, DEFAULT_USER_ID);
                strictInsertFill(metaObject, "finalChgrId", String.class, DEFAULT_USER_ID);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                strictUpdateFill(metaObject, "finalChgDtm", LocalDateTime.class, LocalDateTime.now());
                strictUpdateFill(metaObject, "finalChgrId", String.class, DEFAULT_USER_ID);
            }
        };
    }
}
