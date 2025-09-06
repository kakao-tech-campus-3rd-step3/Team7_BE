package com.careerfit.coverletter.dto;

import com.careerfit.coverletter.domain.CoverLetterItem;

import java.util.List;

public record CoverLetterDetailResponse(
        String title,
        List<CoverLetterItemInfoResponse> coverLetterItems
) {

    public static CoverLetterDetailResponse of(String title, List<CoverLetterItem> coverLetterItems) {
        List<CoverLetterItemInfoResponse> coverLetterItemInfoResponses =
                coverLetterItems.stream()
                        .map(CoverLetterItemInfoResponse::from)
                        .toList();
        return new CoverLetterDetailResponse(title, coverLetterItemInfoResponses);
    }
}
