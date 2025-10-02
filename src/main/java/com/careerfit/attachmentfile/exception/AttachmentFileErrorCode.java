package com.careerfit.attachmentfile.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AttachmentFileErrorCode implements ErrorCode {

    ATTACHMENT_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "ATTACHMENTFILE-001", "파일이 존재하지 않습니다."),
    ATTACHMENT_FILE_NOT_MATCHED(HttpStatus.BAD_REQUEST, "ATTACHMENTFILE-002", "지원항목 id가 일치하지 않습니다."),
    INVALID_ORIGINAL_FILENAME(HttpStatus.BAD_REQUEST, "ATTACHMENTFILE-003", "원본 파일명이 유효하지 않습니다."),
    INVALID_STORED_FILE_PATH(HttpStatus.BAD_REQUEST, "ATTACHMENTFILE-004", "저장 파일 경로가 유효하지 않습니다."),
    INVALID_ATTACHMENT_FILE_TYPE(HttpStatus.BAD_REQUEST, "ATTACHMENTFILE-005", "첨부파일 타입이 유효하지 않습니다."),
    INVALID_DOCUMENT_TITLE(HttpStatus.BAD_REQUEST, "ATTACHMENTFILE-006", "저장 문서명이 유효하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
