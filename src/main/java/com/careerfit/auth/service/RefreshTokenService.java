package com.careerfit.auth.service;

import org.springframework.stereotype.Service;

import com.careerfit.auth.domain.RefreshToken;
import com.careerfit.auth.dto.RefreshTokenInfo;
import com.careerfit.auth.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void cacheRefreshToken(RefreshTokenInfo refreshTokenInfo) {
        refreshTokenRepository.save(RefreshToken.from(refreshTokenInfo));
    }

}
