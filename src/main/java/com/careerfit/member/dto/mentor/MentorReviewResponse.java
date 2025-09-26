package com.careerfit.member.dto.mentor;

import java.time.LocalDateTime;
import java.util.List;

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

