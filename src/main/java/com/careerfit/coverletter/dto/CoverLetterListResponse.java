package com.careerfit.coverletter.dto;

import com.careerfit.coverletter.domain.CoverLetter;

import java.util.List;

public record CoverLetterListResponse(
    List<CoverLetterInfoResponse> coverLetters
) {

    public static CoverLetterListResponse of(List<CoverLetter> coverLetters) {
        List<CoverLetterInfoResponse> coverLetterInfoResponses =
            coverLetters.stream()
                .map(CoverLetterInfoResponse::from)
                .toList();
        return new CoverLetterListResponse(coverLetterInfoResponses);
    }
}
