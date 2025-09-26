package com.careerfit.comment.dto;

import com.careerfit.comment.domain.Coordinate;
import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(
    @NotBlank(message = "댓글 내용을 입력해 주세요.")
    String content,
    Coordinate coordinate
) {
}
