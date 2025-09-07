package com.careerfit.auth.domain;

import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@RedisHash(value = "refresh_token")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    private String refreshToken;

    @Indexed
    private Long userId;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expirationMillis;

    public static RefreshToken of(String refreshToken, Long userId, Long expirationMillis) {
        return new RefreshToken(refreshToken, userId, expirationMillis);
    }

}
