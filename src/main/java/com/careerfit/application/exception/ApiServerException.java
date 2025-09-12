package com.careerfit.application.exception;

import com.careerfit.global.exception.ApplicationException;

public class ApiServerException extends ApplicationException {

    public ApiServerException(String statusText) {
        super(AiServerErrorCode.AI_API_SERVER_ERROR);
        addErrorInfo("statusText", statusText);
    }
}