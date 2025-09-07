package com.careerfit.coverletter.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CoverLetterRegisterRequest(
        @NotBlank(message = "제목은 빈 값일 수 없습니다.")
        String title,
        @Valid
        List<CoverLetterItemInfoRequest> coverLetterItems
) {

}
