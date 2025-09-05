package com.careerfit.application.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AiServerErrorCode implements ErrorCode {
    AI_API_CLIENT_ERROR(HttpStatus.BAD_REQUEST, "AI-001", "AI 서버 API 클라이언트 오류"),
    AI_API_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AI-002", "AI 서버 API 서버 오류");

    private final HttpStatus status;
    private final String code;
    private final String message;
}