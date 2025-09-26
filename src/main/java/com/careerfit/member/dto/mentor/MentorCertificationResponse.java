package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.mentor.MentorCertification;

public record MentorCertificationResponse(
    String certificateName
) {
    public static MentorCertificationResponse from(MentorCertification entity) {
        return new MentorCertificationResponse(entity.getCertificateName());
    }
}
