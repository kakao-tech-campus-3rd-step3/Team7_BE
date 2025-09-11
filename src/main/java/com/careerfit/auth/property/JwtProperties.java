package com.careerfit.auth.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String secretKey,
    long accessTokenExpirationMillis,
    long refreshTokenExpirationMillis,
    String issuer
) {
}

