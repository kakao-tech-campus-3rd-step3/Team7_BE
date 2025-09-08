package com.careerfit.auth.utils;

import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.careerfit.auth.property.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final long ACCESS_TOKEN_EXPIRATION_MILLIS;
    private final long REFRESH_TOKEN_EXPIRATION_MILLIS;
    private final String ISSUER;
    private static final String TOKEN_TYPE = "Bearer";
    private static final String ROLES = "roles";

    public JwtProvider(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
        this.ACCESS_TOKEN_EXPIRATION_MILLIS = jwtProperties.accessTokenExpirationMillis();
        this.REFRESH_TOKEN_EXPIRATION_MILLIS = jwtProperties.refreshTokenExpirationMillis();
        this.ISSUER = jwtProperties.issuer();
    }

    public String createAccessToken(Long userId, Set<String> roles) {
        Claims claims = Jwts.claims()
            .add(ROLES, roles.stream().toList())
            .build();

        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MILLIS);

        return Jwts.builder()
            .issuer(ISSUER)
            .subject(userId.toString())
            .claims(claims)
            .issuedAt(now)
            .expiration(expiredAt)
            .signWith(key)
            .compact();
    }

    public String createRefreshToken(Long userId) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_MILLIS);

        return Jwts.builder()
            .issuer(ISSUER)
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiredDate)
            .signWith(key)
            .compact();
    }

    public String getTokenType() {
        return TOKEN_TYPE;
    }

    public long getAccessTokenExpiration() {
        return ACCESS_TOKEN_EXPIRATION_MILLIS;
    }

    public long getRefreshTokenExpiration() {
        return REFRESH_TOKEN_EXPIRATION_MILLIS;
    }
}

