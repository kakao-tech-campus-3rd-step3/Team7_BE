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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewControllerDocs {

    private final ReviewService reviewService;

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
    public ResponseEntity<ApiResponse<ReviewUpdateResponse>> updateReview(
        @PathVariable Long reviewId,
        @RequestParam Long memberId,
        @Valid @RequestBody ReviewPatchRequest request
    ) {
        Long menteeId = memberId;
        ReviewUpdateResponse response = reviewService.updateReview(reviewId, menteeId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
        @PathVariable Long reviewId,
        @RequestParam Long memberId
    ) {
        Long menteeId = memberId;
        reviewService.deleteReview(reviewId, menteeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
