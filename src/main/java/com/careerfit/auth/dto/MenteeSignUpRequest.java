package com.careerfit.auth.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MenteeSignUpRequest(
    @Valid
    @NotNull
    CommonSignUpRequest commonInfo,
    String university,
    String major,
    @Min(value = 0)
    Integer graduationYear,
    List<String> wishCompanies,
    List<String> wishPositions
) {

}
