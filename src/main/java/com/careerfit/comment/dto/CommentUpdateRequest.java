package com.careerfit.comment.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
    @NotBlank(message = "댓글 내용은 비워둘 수 없습니다.")
    String content
) {
}
