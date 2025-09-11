package com.careerfit.auth.utils;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.careerfit.auth.property.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtValidator {

    private final SecretKey key;

    public JwtValidator(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes());
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
