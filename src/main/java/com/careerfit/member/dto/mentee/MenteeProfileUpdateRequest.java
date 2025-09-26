package com.careerfit.member.dto.mentee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MenteeProfileUpdateRequest(
    @NotBlank String name,
    @Email String email,
    @NotBlank String phoneNumber,
    @NotBlank String university,
    @NotBlank String major,
    @NotNull Integer graduationYear,
    List<MenteeWishCompanyRequest> wishCompanies,
    List<MenteeWishPositionRequest> wishPositions
) {
}