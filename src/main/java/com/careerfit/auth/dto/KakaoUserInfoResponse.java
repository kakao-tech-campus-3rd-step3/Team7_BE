package com.careerfit.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public record KakaoUserInfoResponse(
    Long id,
    Properties properties
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Properties(
        String nickname,
        String profileImage
    ) {

    }
}
