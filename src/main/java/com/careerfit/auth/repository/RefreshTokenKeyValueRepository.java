package com.careerfit.auth.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

import com.careerfit.auth.domain.RefreshToken;

public interface RefreshTokenKeyValueRepository extends KeyValueRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String refreshToken);

}
