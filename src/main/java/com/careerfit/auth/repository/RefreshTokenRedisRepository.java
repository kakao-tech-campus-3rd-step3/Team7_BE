package com.careerfit.auth.repository;

import org.springframework.stereotype.Repository;

import com.careerfit.auth.domain.RefreshToken;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRedisRepository implements RefreshTokenRepository {

    private final RefreshTokenKeyValueRepository repository;

    @Override
    public void save(RefreshToken refreshToken) {
        repository.save(refreshToken);
    }

    @Override
    public boolean existsByRefreshToken(final String refreshToken) {
        return repository.existsByRefreshToken(refreshToken);
    }

    @Override
    public boolean existsByUserId(Long id) {
        return repository.existsByUserId(id);
    }

    @Override
    public void delete(final RefreshToken refreshToken) {
        repository.delete(refreshToken);
    }

    @Override
    public void deleteByUserId(Long id) {
        repository.deleteByUserId(id);
    }

}
