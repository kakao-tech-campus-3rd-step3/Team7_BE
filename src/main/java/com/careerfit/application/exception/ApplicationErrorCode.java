package com.careerfit.application.exception;

import org.springframework.http.HttpStatus;

import com.careerfit.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {

    APPLICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "APPLICATION=001", "지원항목을 찾을 수 없습니다."),
    APPLICATION_UNMATCHED(HttpStatus.BAD_REQUEST, "APPLICATION-002", "올바른 지원항목으로 다시 요청해 주세요.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
