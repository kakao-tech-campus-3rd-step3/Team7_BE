package com.careerfit.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ReissueTokenRequest(
    @NotBlank
    String refreshToken
) {

}
