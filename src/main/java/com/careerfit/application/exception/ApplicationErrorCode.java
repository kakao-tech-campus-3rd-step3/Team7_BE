package com.careerfit.application.exception;

import org.springframework.http.HttpStatus;

import com.careerfit.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {

    APPLICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "APPLICATION=001", "지원항목을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
