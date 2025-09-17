package com.careerfit.portfolio.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PortfolioErrorCode implements ErrorCode {

    PORTFOLIO_NOT_FOUND(HttpStatus.NOT_FOUND, "PORTFOLIO-001", "포트폴리오가 존재하지 않습니다."),
    PORTFOLIO_NOT_MATCHED(HttpStatus.BAD_REQUEST, "PORTFOLIO-002", "지원항목 id가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
