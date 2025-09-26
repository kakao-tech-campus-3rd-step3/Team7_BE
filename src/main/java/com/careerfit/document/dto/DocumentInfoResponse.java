package com.careerfit.document.dto;

import com.careerfit.document.domain.Document;

// CommentResponse에 사용할 전용 Document dto입니다.
public record DocumentInfoResponse(
    Long id,
    String title
) {
    public static DocumentInfoResponse from(Document document) {
        return new DocumentInfoResponse(document.getId(), document.getTitle());
    }
}
