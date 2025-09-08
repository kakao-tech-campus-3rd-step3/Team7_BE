package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MentorProfile;

public record MentorListResponse(
    Long id,
    String name,
    String profileImageUrl,
    String company,
    String jobPosition,
    int careerYears,
    double rating,
    int reviewsCount,
    int menteesCount,
    double pricePerSession
) {

    public static MentorListResponse from(Member member) {
        MentorProfile profile = member.getMentoProfile();
        return new MentorListResponse(
            member.getId(),
            member.getName(),
            member.getProfileImageUrl(),
            profile.getCompany(),
            profile.getJobPosition(),
            profile.getCareerYears(),
            profile.getRating(),
            profile.getReviewCount(),
            profile.getMenteeCount(),
            profile.getPricePerSession()
        );
    }
}
