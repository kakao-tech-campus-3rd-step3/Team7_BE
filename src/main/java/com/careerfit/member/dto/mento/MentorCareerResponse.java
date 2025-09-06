package com.careerfit.member.dto.mento;

public record MentorCareerResponse(
        String companyName,
        String position,
        String startDate,
        String endDate
) {
    public static MentorCareerResponse from(com.careerfit.member.domain.MentoCareer career) {
        return new MentorCareerResponse(
                career.getCompanyName(),
                career.getPosition(),
                career.getStartDate(),
                career.getEndDate()
        );
    }
}
