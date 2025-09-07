package com.careerfit.member.dto.mentee;

import java.util.List;

public record MenteeProfileInfo(
        String name,
        String email,
        String phoneNumber,
        String university,
        String major,
        Integer graduationYear,
        List<String> wishCompany,
        List<String> wishPosition
) {}
