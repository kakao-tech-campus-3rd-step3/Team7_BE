package com.careerfit.comment.dto;

import com.careerfit.comment.domain.Coordinate;

public record CommentCreateRequest(
    String content,
    Coordinate coordinate
) {
}
