package com.careerfit.application.exception;

import com.careerfit.global.exception.ApplicationException;

public class ApiClientException extends ApplicationException {

    public ApiClientException(String statusText) {
        super(AiServerErrorCode.AI_API_CLIENT_ERROR);
        addErrorInfo("statusText", statusText);
    }
}