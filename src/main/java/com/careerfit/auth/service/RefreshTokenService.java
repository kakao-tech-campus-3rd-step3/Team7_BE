package com.careerfit.auth.service;

import org.springframework.stereotype.Service;

import com.careerfit.auth.domain.RefreshToken;
import com.careerfit.auth.dto.RefreshTokenInfo;
import com.careerfit.auth.property.JwtProperties;
import com.careerfit.auth.repository.RefreshTokenRepository;
import com.careerfit.auth.utils.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public void cacheRefreshToken(String refreshToken, Long memberId) {
        refreshTokenRepository.save(RefreshToken.of(refreshToken, memberId,
            jwtProvider.getRefreshTokenExpiration()));
    }

}
