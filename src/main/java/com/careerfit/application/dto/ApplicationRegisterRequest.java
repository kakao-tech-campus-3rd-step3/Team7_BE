package com.careerfit.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.URL;

public record ApplicationRegisterRequest(
        @NotBlank(message = "회사명은 비어 있을 수 없습니다.")
        String companyName,

        @NotBlank(message = "지원 직무는 비어 있을 수 없습니다.")
        String applyPosition,

        LocalDateTime deadline,

        String location,

        String employmentType,

        @Min(value = 0, message = "경력 연수는 0 이상이어야 합니다.")
        Integer careerRequirement,

        @URL(message = "유효한 URL 형식이 아닙니다.")
        String url
) {

}
