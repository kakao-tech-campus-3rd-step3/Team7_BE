package com.careerfit.auth.dto;

public record MentorCareerRequest(
    String companyName,
    String position,
    String startDate,
    String endDate
) {

}
