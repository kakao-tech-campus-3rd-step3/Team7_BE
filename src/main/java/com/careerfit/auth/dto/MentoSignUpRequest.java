package com.careerfit.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import com.careerfit.member.domain.MentorCareer;

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
        List<String> educations,
        List<String> expertises,
        String description,
        List<MentorCareer> careers
) {
}
