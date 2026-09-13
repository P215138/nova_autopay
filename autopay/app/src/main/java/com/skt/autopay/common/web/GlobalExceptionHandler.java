package com.skt.autopay.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 전역 예외 핸들러.
 *
 * <p>비즈니스 규칙 위반(IllegalStateException/IllegalArgumentException)을
 * 깔끔한 메시지 JSON(HTTP 400)으로 반환한다. 화면에서 팝업으로 표시하기 쉽도록
 * {@code {"success":false,"message":"..."}} 형태로 응답한다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 비즈니스 규칙 위반 (중복 등록, 상태 전이 불가 등) */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()));
    }

    /** 잘못된 입력 (조회 실패, 유효성 오류 등) */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", e.getMessage()));
    }
}
