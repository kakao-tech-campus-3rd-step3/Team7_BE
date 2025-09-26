package com.careerfit.application.dto;

import java.util.List;

public record ApplicationListResponse(
    List<ApplicationSummaryResponse> applications
) {

    public static ApplicationListResponse from(List<ApplicationSummaryResponse> applications) {
        return new ApplicationListResponse(applications);
    }
}
