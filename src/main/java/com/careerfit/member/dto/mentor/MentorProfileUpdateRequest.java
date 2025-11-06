package com.careerfit.member.dto.mentor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MentorProfileUpdateRequest(
    @NotBlank String name,
    @Email String email,
    @NotBlank String phoneNumber,
    String profileImageUrl,
    Integer careerYears,
    String company,
    String jobPosition,
    @NotBlank String employmentCertificate,
    List<MentorEducationRequest> educations,
    List<MentorCertificationRequest> certifications,
    List<MentorExpertiseRequest> expertises,
    String introduction
) {
}

