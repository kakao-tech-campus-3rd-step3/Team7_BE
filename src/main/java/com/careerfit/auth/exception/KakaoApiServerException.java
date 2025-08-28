package com.careerfit.auth.exception;

import com.careerfit.global.exception.ApplicationException;

public class KakaoApiServerException extends ApplicationException {

    public KakaoApiServerException(String statusText) {
        super(AuthErrorCode.KAKAO_SERVER_ERROR);
        addErrorInfo("statusText", statusText);
    }
}
