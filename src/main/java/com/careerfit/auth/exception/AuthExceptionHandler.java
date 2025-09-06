package com.careerfit.auth.exception;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.global.exception.ErrorCode;
import com.careerfit.global.exception.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    @ExceptionHandler({KakaoApiClientException.class, KakaoApiServerException.class})
    public ResponseEntity<ApiResponse<ErrorInfo>> handleKakaoApiException(
            KakaoApiClientException exception) {
        log.error("카카오 API 에러 발생 : {}", exception.getMessage());

        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage(),
                        ErrorInfo.of(exception.getErrorInfo())));

    }
}
