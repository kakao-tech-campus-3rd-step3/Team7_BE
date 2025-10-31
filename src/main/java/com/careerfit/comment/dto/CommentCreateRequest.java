package com.careerfit.comment.dto;

import com.careerfit.comment.domain.Coordinate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
    @NotBlank(message = "댓글 내용을 입력해 주세요.")
    String content,
    Coordinate coordinate,

    @NotNull(message = "pdf 페이지 정보를 입력해 주세요")
    Integer page
) {
}
