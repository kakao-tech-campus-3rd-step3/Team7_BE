package com.careerfit.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CommonSignUpRequest(
        @NotBlank
        String name,
        @Email
        String email,
        @NotBlank
        String phoneNumber,
        @URL
        String profileImage,
        @NotBlank
        String registrationId,
        @NotBlank
        String oauthId
) {

}
