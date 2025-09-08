package com.careerfit.auth.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MentorSignUpRequest(
        @Valid
        @NotNull
        CommonSignUpRequest commonInfo,
        @NotNull
        @Min(value = 1)
        Integer careerYears,
        @NotBlank
        String currentCompany,
        @NotBlank
        String currentPosition,
        @NotBlank
        String employmentCertificate,
        List<String> certifications,
        List<String> educations,
        List<String> expertises,
        String description,
        List<MentorCareerRequest> careers
) {
}
