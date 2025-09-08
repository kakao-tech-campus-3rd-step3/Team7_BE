package com.careerfit.member.dto.mentee;

import java.util.List;

public record MenteeProfileUpdateRequest(
    String profileImage,
    String university,
    String major,
    Integer graduationYear,
    List<String> wishCompany,
    List<String> wishPosition
) {

}