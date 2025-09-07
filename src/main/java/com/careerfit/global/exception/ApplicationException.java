package com.careerfit.global.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApplicationException extends RuntimeException {

    ErrorCode errorCode;
    Map<String, Object> errorInfo;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorInfo = new HashMap<>();
    }

    public ApplicationException addErrorInfo(String key, Object value) {
        this.errorInfo.put(key, value);
        return this;
    }
}
