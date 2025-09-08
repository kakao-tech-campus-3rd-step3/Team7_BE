package com.careerfit.member.dto.mento;

import java.util.List;

public record MentorIntroductionResponse(
    String introduction,
    List<String> education,
    List<String> expertise,
    List<String> certifications,
    List<MentorCareerResponse> career
) {

}

