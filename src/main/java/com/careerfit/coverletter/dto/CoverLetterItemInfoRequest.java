package com.careerfit.coverletter.dto;

import jakarta.validation.constraints.NotNull;

public record CoverLetterItemInfoRequest(
    @NotNull
    String question,
    @NotNull
    String answer,
    Integer answerLimit
) {

}
