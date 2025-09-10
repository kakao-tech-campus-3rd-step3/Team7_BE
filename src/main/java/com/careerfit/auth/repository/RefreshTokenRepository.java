package com.careerfit.auth.repository;

import com.careerfit.auth.domain.RefreshToken;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);

    boolean existsByRefreshToken(String refreshToken);

    boolean existsByUserId(Long id);

    void delete(RefreshToken refreshToken);

    void deleteByUserId(Long id);

}
