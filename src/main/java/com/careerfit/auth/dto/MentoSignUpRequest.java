package com.careerfit.auth.dto;

import com.careerfit.member.domain.MentoCareer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MentoSignUpRequest(
        @Valid
        @NotNull
        CommonSignUpRequest commonInfo,
        @NotNull
        @Min(value = 1)
        Integer career,
        @NotBlank
        String currentCompany,
        @NotBlank
        String currentPosition,
        @NotBlank
        String employmentCertificate,
        List<String> certifications,
        List<String> education,
        List<String> expertises,
        String description,
        List<MentoCareer> careers
) {
}
