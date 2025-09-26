package com.careerfit.member.dto.mentor;

import jakarta.validation.constraints.NotBlank;

public record MentorEducationRequest(
    @NotBlank String schoolName,
    @NotBlank String major,
    int startYear,
    int endYear
) {
}