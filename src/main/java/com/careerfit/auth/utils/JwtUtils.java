package com.careerfit.auth.utils;


import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.careerfit.auth.dto.TokenInfo;
import com.careerfit.auth.property.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final SecretKey key;
    private final long ACCESS_TOKEN_VALIDITY_MILLIS;
    private final long REFRESH_TOKEN_VALIDITY_MILLIS;
    private final String ISSUER;
    private final String TOKEN_TYPE = "Bearer";

    public JwtUtils(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
        this.ACCESS_TOKEN_VALIDITY_MILLIS = jwtProperties.accessTokenValidityMillis();
        this.REFRESH_TOKEN_VALIDITY_MILLIS = jwtProperties.refreshTokenValidityMillis();
        this.ISSUER = jwtProperties.issuer();
    }

    public String createAccessToken(Long userId, Set<String> roles) {
        Claims claims = Jwts.claims()
            .add("roles", roles.stream().toList())
            .build();

        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_MILLIS);

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
        Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_VALIDITY_MILLIS);

        return Jwts.builder()
            .issuer(ISSUER)
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiredDate)
            .signWith(key)
            .compact();
    }

    public TokenInfo generateTokens(Long userId, Set<String> roles) {
        String accessToken = createAccessToken(userId, roles);
        String refreshToken = createRefreshToken(userId);
        return TokenInfo.of(TOKEN_TYPE, accessToken, refreshToken, ACCESS_TOKEN_VALIDITY_MILLIS,
            REFRESH_TOKEN_VALIDITY_MILLIS);
    }

    public Long getUserId(String token) {
        return Long.parseLong(
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

