package com.careerfit.review.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.review.dto.*;
import com.careerfit.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 멘토 상세조회 - 리뷰 목록 조회 (Read)
     */
    @GetMapping("/mentor/{mentorId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ReviewGetResponse> getReviewsByMento(@PathVariable Long mentorId) {
        ReviewGetResponse response = reviewService.getReviewsByMentor(mentorId);
        return ApiResponse.success(response);
    }

    @PostMapping("/mentor/{mentorId}")
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

    @PatchMapping("/{reviewId}")
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

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
        @PathVariable Long reviewId,
        @RequestParam Long memberId
    ) {
        Long menteeId = memberId;
        reviewService.deleteReview(reviewId, menteeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
