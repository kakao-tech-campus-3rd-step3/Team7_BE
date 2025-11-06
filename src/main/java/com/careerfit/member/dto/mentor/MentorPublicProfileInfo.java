package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.mentor.MentorProfile;

import java.util.List;
import java.util.stream.Collectors;

public record MentorPublicProfileInfo(
    String name,
    String profileImageUrl,
    Integer careerYears,
    String company,
    String jobPosition,
    List<MentorEducationResponse> educations,
    List<MentorCertificationResponse> certifications,
    List<MentorExpertiseResponse> expertises,
    String introduction,
    Double averageRating,
    Integer reviewCount
) {
    public static MentorPublicProfileInfo from(MentorProfile profile) {
        return new MentorPublicProfileInfo(
            profile.getMember().getName(),
            profile.getMember().getProfileImageUrl(),
            profile.getCareerYears(),
            profile.getCompany(),
            profile.getJobPosition(),
            profile.getEducations().stream()
                .map(MentorEducationResponse::from)
                .collect(Collectors.toList()),
            profile.getCertifications().stream()
                .map(MentorCertificationResponse::from)
                .collect(Collectors.toList()),
            profile.getExpertises().stream()
                .map(MentorExpertiseResponse::from)
                .collect(Collectors.toList()),
            profile.getIntroduction(),
            profile.getAverageRating(),
            profile.getReviewCount()
        );
    }
}
