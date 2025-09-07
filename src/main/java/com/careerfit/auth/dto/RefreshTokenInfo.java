package com.careerfit.auth.dto;

public record RefreshTokenInfo(
    String refreshToken,
    Long userId,
    Long expirationInMillis
) {

    public static RefreshTokenInfo of(String refreshToken, Long userId, Long expirationInMillis){
        return new RefreshTokenInfo(refreshToken, userId, expirationInMillis);
    }

}
