package com.careerfit.auth.repository;

import com.careerfit.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRedisRepository implements RefreshTokenRepository {

    private final RefreshTokenKeyValueRepository repository;

    @Override
    public void save(RefreshToken refreshToken) {
        repository.save(refreshToken);
    }

    @Override
    public boolean existsByRefreshToken(String refreshToken) {
        return repository.existsByRefreshToken(refreshToken);
    }

    @Override
    public boolean existsByUserId(Long id) {
        return repository.existsById(id);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        repository.delete(refreshToken);
    }

    @Override
    public void deleteByUserId(Long id) {
        repository.deleteById(id);
    }

}
