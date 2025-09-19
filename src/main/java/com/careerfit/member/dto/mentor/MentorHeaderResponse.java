package com.careerfit.member.dto.mentor;

public record MentorHeaderResponse(
    Long id,
    String name,
    String company,
    String jobPosition,
    double averageRating,
    int reviewsCount,
    int experience,
    int menteesCount
) {

}
