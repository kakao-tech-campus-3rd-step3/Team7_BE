package com.careerfit.application.dto;

import java.time.LocalDateTime;

public record JobPostingAnalysisResponse(
        String companyName,
        String applyPosition,
        LocalDateTime deadline,
        String location,
        String employmentType,
        String careerRequirement
) {

}
