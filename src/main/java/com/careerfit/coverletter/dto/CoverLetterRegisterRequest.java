package com.careerfit.coverletter.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CoverLetterRegisterRequest(
    @NotBlank(message = "제목은 빈 값일 수 없습니다.")
    String title,
    @Valid
    List<CoverLetterItemInfoRequest> coverLetterItems
) {

}
