package com.careerfit.comment.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT-001", "코멘트가 존재하지 않습니다."),
    NOT_ALLOWED_TO_QUERY(HttpStatus.FORBIDDEN, "COMMENT-002", "코멘트를 조회할 권한이 없습니다."),
    NOT_ALLOWED_TO_COMMAND(HttpStatus.FORBIDDEN, "COMMENT-003", "코멘트를 수정/삭제할 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
