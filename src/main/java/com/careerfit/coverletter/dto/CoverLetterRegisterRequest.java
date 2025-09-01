package com.careerfit.coverletter.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CoverLetterRegisterRequest(
    @NotBlank
    String title,
    @Valid
    List<CoverLetterItemInfo> coverLetterItems
) {

}
