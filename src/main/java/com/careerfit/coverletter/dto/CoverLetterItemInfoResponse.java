package com.careerfit.coverletter.dto;

import com.careerfit.coverletter.domain.CoverLetterItem;

public record CoverLetterItemInfoResponse(
    String question,
    String answer,
    Integer answerLimit
) {
    public static CoverLetterItemInfoResponse from(CoverLetterItem coverLetterItem) {
        return new CoverLetterItemInfoResponse(coverLetterItem.getQuestion(), coverLetterItem.getAnswer(), coverLetterItem.getAnswerLimit());
    }
}