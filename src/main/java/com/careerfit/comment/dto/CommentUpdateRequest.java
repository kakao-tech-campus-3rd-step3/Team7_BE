package com.careerfit.comment.dto;

public record CommentUpdateRequest(
    String content,
    Integer page
) {

}
