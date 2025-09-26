package com.careerfit.member.dto.mentor;

import java.util.List;

public record MentorProfileUpdateRequest(
    String name,
    String email,
    String phoneNumber,
    String profileImageUrl,
    Integer careerYears,
    String company,
    String jobPosition,
    String employmentCertificate,
    List<MentorEducationRequest> educations,
    List<MentorCertificationRequest> certifications,
    List<MentorExpertiseRequest> expertises,
    String introduction
) {
}

