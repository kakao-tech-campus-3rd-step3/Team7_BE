package com.careerfit.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewPostRequest(
    @Min(value = 1, message = "평점은 1점 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5점 이하이어야 합니다.")
    double rating,

    @NotBlank(message = "리뷰 내용은 비어 있을 수 없습니다.")
    String content
) {

}
