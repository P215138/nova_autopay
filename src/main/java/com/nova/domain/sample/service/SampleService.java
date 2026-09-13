package com.nova.domain.sample.service;

import com.nova.domain.sample.dto.SampleDto;
import com.nova.domain.sample.entity.Sample;
import com.nova.domain.sample.repository.SampleRepository;
import com.nova.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SampleService {

    private final SampleRepository sampleRepository;

    public List<SampleDto.Response> findAll() {
        return sampleRepository.findAll().stream()
                .map(SampleDto.Response::from)
                .collect(Collectors.toList());
    }

    public SampleDto.Response findById(Long id) {
        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Sample을 찾을 수 없습니다. id=" + id));
        return SampleDto.Response.from(sample);
    }

    @Transactional
    public SampleDto.Response create(SampleDto.CreateRequest request) {
        Sample sample = Sample.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return SampleDto.Response.from(sampleRepository.save(sample));
    }

    @Transactional
    public SampleDto.Response update(Long id, SampleDto.UpdateRequest request) {
        Sample sample = sampleRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("Sample을 찾을 수 없습니다. id=" + id));
        sample.update(request.getName(), request.getDescription());
        return SampleDto.Response.from(sample);
    }

    @Transactional
    public void delete(Long id) {
        if (!sampleRepository.existsById(id)) {
            throw BusinessException.notFound("Sample을 찾을 수 없습니다. id=" + id);
        }
        sampleRepository.deleteById(id);
    }
}
