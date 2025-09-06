package com.careerfit.member.dto.mento;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MentoProfile;

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
        MentoProfile profile = member.getMentoProfile();
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
