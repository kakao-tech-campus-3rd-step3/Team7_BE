package com.careerfit.member.dto;

import jakarta.validation.Valid;

public record MenteeSignUpRequest(
    @Valid
    CommonSignUpRequest commonInfo,
    String university,
    String major,
    Integer graduationYear,
    String wishCompanies,
    String wishPositions
) {

}
