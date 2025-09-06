package com.careerfit.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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
