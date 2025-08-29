package com.careerfit.member.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
    String oauthProvider,
    @NotBlank
    String oauthId
) {

}
