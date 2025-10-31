package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;

public record MentorListResponse(
    Long id,
    String name,
    String profileImageUrl,
    String company,
    String jobPosition,
    Integer careerYears,
    Double averageRating,
    Integer reviewCount,
    Integer menteeCount,
    Integer pricePerSession
) {

    public static MentorListResponse from(Member member) {
        MentorProfile profile = member.getMentorProfile();
        return new MentorListResponse(
            member.getId(),
            member.getName(),
            member.getProfileImageUrl(),
            profile.getCompany(),
            profile.getJobPosition(),
            profile.getCareerYears(),
            profile.getAverageRating(),
            profile.getReviewCount(),
            profile.getMenteeCount(),
            profile.getPricePerSession()
        );
    }
}
