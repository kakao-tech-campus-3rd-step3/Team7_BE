package com.careerfit.review.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReviewGetResponse(
    long reviewCount,
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
