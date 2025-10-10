package com.careerfit.document.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum DocumentErrorCode implements ErrorCode {
    DOCUMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "DOCUMENT-001", "문서가 존재하지 않습니다."),
    DOCUMENT_INVALID_TYPE(HttpStatus.BAD_REQUEST, "DOCUMENT-002", "문서 타입을 정확하게 입력해 주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
