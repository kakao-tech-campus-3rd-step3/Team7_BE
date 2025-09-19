package com.careerfit.coverletter.dto;

import com.careerfit.coverletter.domain.CoverLetter;

import java.time.LocalDateTime;

public record CoverLetterInfoResponse(
        Long versionId,
        String title,
        LocalDateTime createdDate
) {
    public static CoverLetterInfoResponse from(CoverLetter coverLetter) {
        return new CoverLetterInfoResponse(coverLetter.getId(), coverLetter.getTitle(), coverLetter.getCreatedDate());
    }
}
