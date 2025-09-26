package com.careerfit.attachmentfile.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AttachmentFileErrorCode implements ErrorCode {

    ATTACHMENT_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "ATTACHMENTFILE-001", "파일이 존재하지 않습니다."),
    ATTACHMENT_FILE_NOT_MATCHED(HttpStatus.BAD_REQUEST, "ATTACHMENTFILE-002", "지원항목 id가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
