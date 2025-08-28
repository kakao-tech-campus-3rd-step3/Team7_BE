package com.careerfit.global.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    ErrorCode errorCode;
    Map<String, Object> errorInfo;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public void addErrorInfo(String key, Object value) {
        this.errorInfo.put(key, value);
    }
}
