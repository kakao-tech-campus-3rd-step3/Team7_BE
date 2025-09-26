package com.careerfit.auth.dto;

import com.careerfit.member.dto.mentee.MenteeWishCompanyRequest;
import com.careerfit.member.dto.mentee.MenteeWishPositionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MenteeSignUpRequest(
    @Valid
    @NotNull
    CommonSignUpRequest commonInfo,
    String university,
    String major,
    @Min(value = 0)
    Integer graduationYear,
    List<MenteeWishCompanyRequest> wishCompanies,
    List<MenteeWishPositionRequest> wishPositions
) {

}
