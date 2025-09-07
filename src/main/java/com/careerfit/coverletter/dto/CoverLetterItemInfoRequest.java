package com.careerfit.coverletter.dto;

import jakarta.validation.constraints.NotNull;

public record CoverLetterItemInfoRequest(
        @NotNull(message = "질문은 빈 값일 수 없습니다.")
        String question,
        @NotNull(message = "답변은 빈 값일 수 없습니다.")
        String answer,
        Integer answerLimit
) {

}
