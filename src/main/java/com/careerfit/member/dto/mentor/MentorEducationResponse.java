package com.careerfit.member.dto.mentor;

import com.careerfit.member.domain.mentor.MentorEducation;

public record MentorEducationResponse(
    String schoolName,
    String major,
    int startYear,
    int endYear
) {
    public static MentorEducationResponse from(MentorEducation entity) {
        return new MentorEducationResponse(
            entity.getSchoolName(),
            entity.getMajor(),
            entity.getStartYear(),
            entity.getEndYear()
        );
    }
}
