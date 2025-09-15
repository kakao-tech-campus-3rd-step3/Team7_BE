package com.careerfit.comment.dto;

import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.domain.Coordinate;
import com.careerfit.document.dto.DocumentInfoResponse;
import com.careerfit.member.dto.MemberInfoResponse;

public record CommentResponse(
    Long id,
    String content,
    Coordinate coordinate,
    MemberInfoResponse writerInfo,
    DocumentInfoResponse documentInfo
) {
    public static CommentResponse from(Comment comment){
        return new CommentResponse(
            comment.getId(),
            comment.getContent(),
            comment.getCoordinate(),
            MemberInfoResponse.from(comment.getMember()),
            DocumentInfoResponse.from(comment.getDocument())
        );
    }
}
