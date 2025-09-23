package com.careerfit.application.dto;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;

import java.time.LocalDateTime;

public record ApplicationDetailHeaderResponse(
    Long applicationId,
    String companyName,
    String applyPosition,
    LocalDateTime deadline,
    String location,
    String employmentType,
    Integer careerRequirement,
    ApplicationStatus applicationStatus
) {

    public static ApplicationDetailHeaderResponse from(Application application) {
        return new ApplicationDetailHeaderResponse(
            application.getId(),
            application.getCompanyName(),
            application.getApplyPosition(),
            application.getDeadLine(),
            application.getLocation(),
            application.getEmploymentType(),
            application.getCareerRequirement(),
            application.getApplicationStatus()
        );
    }
}
