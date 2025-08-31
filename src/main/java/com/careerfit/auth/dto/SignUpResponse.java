package com.careerfit.auth.dto;

public record SignUpResponse(
    Long memberId,
    TokenInfo tokenInfo
) {

    public static SignUpResponse of(Long memberId, TokenInfo tokenInfo) {
        return new SignUpResponse(memberId, tokenInfo);
    }
}
