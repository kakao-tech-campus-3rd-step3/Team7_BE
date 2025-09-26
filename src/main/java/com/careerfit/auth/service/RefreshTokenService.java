package com.careerfit.auth.service;

import com.careerfit.auth.domain.RefreshToken;
import com.careerfit.auth.repository.RefreshTokenRepository;
import com.careerfit.auth.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public void cacheRefreshToken(Long memberId, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.of(memberId, refreshToken,
            jwtProvider.getRefreshTokenExpiration()));
    }

    public boolean existsByRefreshToken(String refreshToken) {
        return refreshTokenRepository.existsByRefreshToken(refreshToken);
    }

    public void deleteByMemberId(Long memberId) {
        refreshTokenRepository.deleteByUserId(memberId);
    }

}
