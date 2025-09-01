package com.careerfit.coverletter.dto;

import java.util.List;

import com.careerfit.coverletter.domain.CoverLetter;

public record CoverLetterListResponse(
    List<CoverLetterInfoResponse> coverLetters
) {

    public static CoverLetterListResponse of(List<CoverLetter> coverLetters) {
        List<CoverLetterInfoResponse> coverLetterInfoResponses =
            coverLetters.stream()
            .map(CoverLetterInfoResponse::of)
            .toList();
        return new CoverLetterListResponse(coverLetterInfoResponses);
    }
}
