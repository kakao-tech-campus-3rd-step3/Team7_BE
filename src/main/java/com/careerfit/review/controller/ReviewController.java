package com.careerfit.review.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.review.dto.ReviewPatchRequest;
import com.careerfit.review.dto.ReviewPostRequest;
import com.careerfit.review.dto.ReviewPostResponse;
import com.careerfit.review.dto.ReviewUpdateResponse;
import com.careerfit.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/mentors/{mentorId}/reviews")
    public ResponseEntity<ApiResponse<ReviewPostResponse>> createReview(
            @PathVariable Long mentorId,
            @RequestParam Long memberId,
            @Valid @RequestBody ReviewPostRequest request
    ) {
        Long menteeId = memberId;
        ReviewPostResponse response = reviewService.createReview(menteeId, mentorId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PatchMapping("/reviews/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ReviewUpdateResponse> updateReview(
            @PathVariable Long reviewId,
            @RequestParam Long memberId,
            @Valid @RequestBody ReviewPatchRequest request
    ) {
        Long menteeId = memberId;
        ReviewUpdateResponse response = reviewService.updateReview(reviewId, menteeId, request);
        return ApiResponse.success(response);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestParam Long memberId
    ) {
        Long menteeId = memberId;
        reviewService.deleteReview(reviewId, menteeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
