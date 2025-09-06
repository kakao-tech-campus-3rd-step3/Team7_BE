package com.careerfit.document.exception;

import com.careerfit.global.exception.ErrorCode;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class FileException extends RuntimeException {

    ErrorCode errorCode;
    Map<String, Object> errorInfo;

    public FileException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorInfo = new HashMap<>();
    }

    public FileException addErrorInfo(String key, Object value) {
        this.errorInfo.put(key, value);
        return this;
    }
}
