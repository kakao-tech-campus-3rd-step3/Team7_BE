package com.careerfit.member.dto.mentor;

import java.util.List;

import java.time.LocalDateTime;

public record MentorReviewResponse(
    int reviewCount,
    double averageRating,
    List<ReviewDetail> reviews
) {

    public record ReviewDetail(
        Long reviewerId,
        String reviewerName,
        double rating,
        String content,
        LocalDateTime createdAt
    ) {

    }
}

