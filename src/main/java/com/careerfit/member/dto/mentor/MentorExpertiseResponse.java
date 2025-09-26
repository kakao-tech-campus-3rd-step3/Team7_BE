package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.mentor.MentorExpertise;

public record MentorExpertiseResponse(
    String expertiseName
) {
    public static MentorExpertiseResponse from(MentorExpertise entity) {
        return new MentorExpertiseResponse(entity.getExpertiseName());
    }
}
