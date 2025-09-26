package com.careerfit.application.dto;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;

import java.time.LocalDateTime;

public record ApplicationSummaryResponse(
    Long applicationId,
    String companyName,
    String applyPosition,
    LocalDateTime deadline,
    ApplicationStatus applicationStatus
) {

    public static ApplicationSummaryResponse from(Application application) {
        return new ApplicationSummaryResponse(
            application.getId(),
            application.getCompanyName(),
            application.getApplyPosition(),
            application.getDeadLine(),
            application.getApplicationStatus()
        );
    }
}
