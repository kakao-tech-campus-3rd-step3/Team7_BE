package com.careerfit.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public record ApplicationRegisterRequest(
    @NotBlank(message = "회사명은 비어 있을 수 없습니다.")
    String companyName,

    @NotBlank(message = "지원 직무는 비어 있을 수 없습니다.")
    String applyPosition,

    LocalDateTime deadline,

    String location,

    String employmentType,

    @PositiveOrZero(message = "경력 요구사항은 음수가 될 수 없습니다")
    Integer careerRequirement,

    @URL(message = "유효한 URL 형식이 아닙니다.")
    String url
) {

}
