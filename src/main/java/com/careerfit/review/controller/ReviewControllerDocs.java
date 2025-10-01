package com.careerfit.review.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.review.dto.ReviewPatchRequest;
import com.careerfit.review.dto.ReviewPostRequest;
import com.careerfit.review.dto.ReviewPostResponse;
import com.careerfit.review.dto.ReviewUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "리뷰 관리 API", description = "멘토에 대한 리뷰를 작성, 수정, 삭제하는 API")
public interface ReviewControllerDocs {

    @Operation(summary = "리뷰 작성", description = "특정 멘토에 대한 멘토링 리뷰를 작성합니다.")
    ResponseEntity<ApiResponse<ReviewPostResponse>> createReview(
        @PathVariable Long mentorId,
        @RequestParam Long memberId,
        @Valid @RequestBody ReviewPostRequest request
    );

    @Operation(summary = "리뷰 수정", description = "자신이 작성한 리뷰의 내용이나 평점을 수정합니다.")
    ResponseEntity<ApiResponse<ReviewUpdateResponse>> updateReview(
        @PathVariable Long reviewId,
        @RequestParam Long memberId,
        @Valid @RequestBody ReviewPatchRequest request
    );

    @Operation(summary = "리뷰 삭제", description = "자신이 작성한 리뷰를 삭제합니다.")
    ResponseEntity<Void> deleteReview(
        @PathVariable Long reviewId,
        @RequestParam Long memberId
    );
}