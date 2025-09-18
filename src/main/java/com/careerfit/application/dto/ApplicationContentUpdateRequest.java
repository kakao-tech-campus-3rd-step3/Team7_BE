package com.careerfit.application.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

public record ApplicationContentUpdateRequest(
        @NotBlank(message = "회사명은 비어 있을 수 없습니다.")
        String companyName,

        @NotBlank(message = "지원 직무는 비어 있을 수 없습니다.")
        String applyPosition,

        @Future(message = "마감일은 현재보다 미래 시점이어야 합니다.")
        LocalDateTime deadline,

        String location,

        String employmentType,

        @PositiveOrZero(message = "경력 요구사항은 음수가 될 수 없습니다.")
        Integer careerRequirement
) {

}
