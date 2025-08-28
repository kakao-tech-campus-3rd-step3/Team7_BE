package com.careerfit.auth.exception;

import org.springframework.http.HttpStatus;

import com.careerfit.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH-001", "인증정보가 유효하지 않습니다"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH-002", "요청에 대한 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
