package com.careerfit.auth.exception;

import com.careerfit.global.exception.ApplicationException;

public class KakaoApiClientException extends ApplicationException {

    public KakaoApiClientException(String statusText) {
        super(AuthErrorCode.KAKAO_CLIENT_ERROR);
        addErrorInfo("statusText", statusText);
    }
}
