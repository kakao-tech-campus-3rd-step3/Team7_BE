package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.MentorCareer;

public record MentorCareerResponse(
    String companyName,
    String position,
    String startDate,
    String endDate
) {

    public static MentorCareerResponse from(MentorCareer career) {
        return new MentorCareerResponse(
            career.getCompanyName(),
            career.getPosition(),
            career.getStartDate(),
            career.getEndDate()
        );
    }
}
