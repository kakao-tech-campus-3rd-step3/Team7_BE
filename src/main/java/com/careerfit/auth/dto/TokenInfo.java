package com.careerfit.auth.dto;

public record TokenInfo(
    String tokenType,
    String accessToken,
    String refreshToken,
    Long accessTokenExpireTimeMillis,
    Long refreshTokenExpireTimeMillis
) {

    public static TokenInfo of(
        String tokenType,
        String accessToken,
        String refreshToken,
        Long accessTokenExpireTimeMillis,
        Long refreshTokenExpireTimeMillis

    ) {
        return new TokenInfo(tokenType, accessToken, refreshToken, accessTokenExpireTimeMillis,
            refreshTokenExpireTimeMillis);
    }
}
