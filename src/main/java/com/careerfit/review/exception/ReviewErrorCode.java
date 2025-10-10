package com.careerfit.review.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW-001", "해당 리뷰를 찾을 수 없습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "REVIEW-002", "해당 리뷰에 대한 접근 권한이 없습니다."),
    CANNOT_REVIEW_SELF(HttpStatus.BAD_REQUEST, "REVIEW-003", "자기 자신에게 리뷰를 작성할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}