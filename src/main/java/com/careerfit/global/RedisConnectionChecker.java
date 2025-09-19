package com.careerfit.global;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Profile({"local"})
@Component
@RequiredArgsConstructor
public class RedisConnectionChecker implements CommandLineRunner {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Checking Redis connection...");
        try {
            // Redis에 Ping 명령어를 보냅니다.
            // ping은 Redis와의 연결 상태를 확인하는 가장 간단한 명령어입니다.
            String result = redisTemplate.getConnectionFactory().getConnection().ping();
            if ("PONG".equals(result)) {
                System.out.println("✅ Redis connection successful: " + result);
            } else {
                System.out.println("❌ Unexpected response from Redis: " + result);
            }
        } catch (Exception e) {
            System.err.println("❌ Redis connection failed: " + e.getMessage());
        }
        System.out.println(">>> Redis check finished.");
    }
}
