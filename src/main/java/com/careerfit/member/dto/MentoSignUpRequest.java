package com.careerfit.member.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MentoSignUpRequest (
    CommonSignUpRequest commonInfo,
    @NotNull
    @Min(value = 1)
    Integer career,
    @NotBlank
    String currentCompany,
    @NotBlank
    String currentPosition,
    @NotBlank
    String certificate,
    String education,
    String expertises,
    String description
){
}
