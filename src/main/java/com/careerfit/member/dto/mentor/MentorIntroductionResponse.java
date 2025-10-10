package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.mentor.MentorProfile;

import java.util.List;
import java.util.stream.Collectors;

public record MentorIntroductionResponse(
    String introduction,
    List<MentorEducationResponse> educations,
    List<MentorExpertiseResponse> expertises,
    List<MentorCertificationResponse> certifications,
    List<MentorCareerResponse> careers
) {
    public static MentorIntroductionResponse from(MentorProfile profile) {
        return new MentorIntroductionResponse(
            profile.getIntroduction(),
            profile.getEducations().stream()
                .map(MentorEducationResponse::from)
                .collect(Collectors.toList()),
            profile.getExpertises().stream()
                .map(MentorExpertiseResponse::from)
                .collect(Collectors.toList()),
            profile.getCertifications().stream()
                .map(MentorCertificationResponse::from)
                .collect(Collectors.toList()),
            profile.getMentorCareers().stream()
                .map(MentorCareerResponse::from)
                .collect(Collectors.toList())
        );
    }
}


