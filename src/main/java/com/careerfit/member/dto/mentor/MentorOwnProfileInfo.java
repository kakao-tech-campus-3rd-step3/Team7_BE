package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;

import java.util.List;

public record MentorOwnProfileInfo(
    String name,
    String email,
    String phoneNumber,
    String profileImageUrl,
    int careerYears,
    String company,
    String jobPosition,
    String employmentCertificate,
    List<MentorEducationResponse> educations,
    List<MentorCertificationResponse> certifications,
    List<MentorExpertiseResponse> expertises,
    String introduction
) {
    public static MentorOwnProfileInfo from(Member member, MentorProfile profile) {
        return new MentorOwnProfileInfo(
            member.getName(),
            member.getEmail(),
            member.getPhoneNumber(),
            member.getProfileImageUrl(),
            profile.getCareerYears(),
            profile.getCompany(),
            profile.getJobPosition(),
            profile.getEmploymentCertificate(),
            profile.getEducations().stream().map(MentorEducationResponse::from).toList(),
            profile.getCertifications().stream().map(MentorCertificationResponse::from).toList(),
            profile.getExpertises().stream().map(MentorExpertiseResponse::from).toList(),
            profile.getIntroduction()
        );
    }
}

