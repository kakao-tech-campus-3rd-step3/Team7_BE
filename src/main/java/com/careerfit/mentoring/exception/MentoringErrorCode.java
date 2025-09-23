package com.careerfit.mentoring.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MentoringErrorCode implements ErrorCode {
    MENTORING_NOT_FOUND(HttpStatus.NOT_FOUND, "MENTORING-001", "멘토링을 찾을 수 없습니다."),
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "DOCUMENT-001", "해당 문서가 없습니다."),
    UNAUTHORIZED_DELETE(HttpStatus.FORBIDDEN, "AUTH-001", "삭제 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
