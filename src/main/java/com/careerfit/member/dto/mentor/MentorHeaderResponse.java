package com.careerfit.member.dto.mentor;

public record MentorHeaderResponse(
    Long id,
    String name,
    String company,
    String jobPosition,
    double rating,
    int reviewsCount,
    int experience,
    int menteesCount
) {

}
