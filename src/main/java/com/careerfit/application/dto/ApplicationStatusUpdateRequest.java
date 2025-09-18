package com.careerfit.application.dto;

import com.careerfit.application.domain.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicationStatusUpdateRequest(
        @NotNull(message = "변경할 지원 상태는 비어 있을 수 없습니다.")
        ApplicationStatus newStatus
) {

}
