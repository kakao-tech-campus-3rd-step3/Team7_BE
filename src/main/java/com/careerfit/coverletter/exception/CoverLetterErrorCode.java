package com.careerfit.coverletter.exception;

import org.springframework.http.HttpStatus;

import com.careerfit.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoverLetterErrorCode implements ErrorCode {

    COVER_LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "COVERLETTER-001", "자기소개서가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
