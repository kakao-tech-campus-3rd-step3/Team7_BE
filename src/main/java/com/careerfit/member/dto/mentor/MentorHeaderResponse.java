package com.careerfit.member.dto.mentor;

public record MentorHeaderResponse(
    Long id,
    String name,
    String profileImageUrl,
    String company,
    String jobPosition,
    Double averageRating,
    Integer reviewsCount,
    Integer experience,
    Integer menteesCount,
    Integer pricePerSession
) {

}
