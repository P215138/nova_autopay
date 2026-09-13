package com.nova.domain.sample.controller;

import com.nova.domain.sample.dto.SampleDto;
import com.nova.domain.sample.service.SampleService;
import com.nova.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/samples")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    // 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<SampleDto.Response>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(sampleService.findAll()));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SampleDto.Response>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(sampleService.findById(id)));
    }

    // 생성
    @PostMapping
    public ResponseEntity<ApiResponse<SampleDto.Response>> create(
            @Valid @RequestBody SampleDto.CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("생성되었습니다.", sampleService.create(request)));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SampleDto.Response>> update(
            @PathVariable Long id,
            @Valid @RequestBody SampleDto.UpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("수정되었습니다.", sampleService.update(id, request)));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        sampleService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("삭제되었습니다.", null));
    }
}
