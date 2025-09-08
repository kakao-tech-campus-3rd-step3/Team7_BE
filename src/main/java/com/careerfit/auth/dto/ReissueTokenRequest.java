package com.careerfit.auth.dto;

import jakarta.validation.constraints.NotNull;

public record ReissueTokenRequest(
    @NotNull
    String refreshToken
) {

}
