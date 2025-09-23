package com.careerfit.member.dto.mentor;

public record MentorEducationRequest(
    String schoolName,
    String major,
    int startYear,
    int endYear
) {
}