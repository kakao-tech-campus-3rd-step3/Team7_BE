package com.careerfit.auth.dto;

import com.careerfit.member.dto.mentor.MentorCertificationRequest;
import com.careerfit.member.dto.mentor.MentorEducationRequest;
import com.careerfit.member.dto.mentor.MentorExpertiseRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MentorSignUpRequest(
    @Valid
    @NotNull
    CommonSignUpRequest commonInfo,
    @NotNull
    @Min(value = 1)
    Integer careerYears,
    @NotBlank
    String currentCompany,
    @NotBlank
    String currentPosition,
    @NotBlank
    String employmentCertificate,
    List<MentorCertificationRequest> certifications,
    List<MentorEducationRequest> educations,
    List<MentorExpertiseRequest> expertises,
    String description,
    List<MentorCareerRequest> careers
) {

}
