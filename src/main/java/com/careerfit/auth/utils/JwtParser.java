package com.careerfit.auth.utils;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.careerfit.auth.property.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtParser {

    private final SecretKey key;

    public JwtParser(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
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

}
