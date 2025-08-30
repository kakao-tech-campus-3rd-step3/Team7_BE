package com.careerfit.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MentoSignUpRequest (
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
    String certificate,
    String education,
    String expertises,
    String description
){
}
