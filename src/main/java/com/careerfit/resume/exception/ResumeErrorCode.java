package com.careerfit.resume.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResumeErrorCode implements ErrorCode {

    RESUME_NOT_FOUND(HttpStatus.NOT_FOUND, "RESUME-001", "이력서가 존재하지 않습니다."),
    RESUME_NOT_MATCHED(HttpStatus.BAD_REQUEST, "RESUME-002", "지원항목 id가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
