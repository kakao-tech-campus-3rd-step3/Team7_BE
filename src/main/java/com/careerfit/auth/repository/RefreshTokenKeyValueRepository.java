package com.careerfit.auth.repository;

import com.careerfit.auth.domain.RefreshToken;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface RefreshTokenKeyValueRepository extends KeyValueRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String refreshToken);

}
