package com.careerfit.member.dto.mentee;

import java.util.List;

public record MenteeProfileUpdateRequest(
    String name,
    String email,
    String phoneNumber,
    String university,
    String major,
    Integer graduationYear,
    List<MenteeWishCompanyRequest> wishCompanies,
    List<MenteeWishPositionRequest> wishPositions
) {
}