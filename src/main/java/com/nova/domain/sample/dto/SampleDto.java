package com.nova.domain.sample.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class SampleDto {

    @Getter
    public static class CreateRequest {
        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 100, message = "이름은 100자 이하여야 합니다.")
        private String name;

        @Size(max = 255, message = "설명은 255자 이하여야 합니다.")
        private String description;
    }

    @Getter
    public static class UpdateRequest {
        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 100, message = "이름은 100자 이하여야 합니다.")
        private String name;

        @Size(max = 255, message = "설명은 255자 이하여야 합니다.")
        private String description;
    }

    @Getter
    public static class Response {
        private final Long id;
        private final String name;
        private final String description;

        public Response(Long id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }

        public static Response from(com.nova.domain.sample.entity.Sample sample) {
            return new Response(sample.getId(), sample.getName(), sample.getDescription());
        }
    }
}
