package com.careerfit.application.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.URL;

public record ApplicationRegisterRequest(
        @NotBlank(message = "회사명은 비어 있을 수 없습니다.")
        String companyName,

        @NotBlank(message = "지원 직무는 비어 있을 수 없습니다.")
        String applyPosition,

        @Future(message = "마감일은 현재보다 미래 시점이어야 합니다.")
        LocalDateTime deadline,

        String location,

        String employmentType,

        String careerRequirement,

        @URL(message = "유효한 URL 형식이 아닙니다.")
        String url
) {

}
