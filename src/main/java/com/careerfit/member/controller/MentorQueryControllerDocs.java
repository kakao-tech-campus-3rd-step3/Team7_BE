package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentor.MentorDashboardItem;
import com.careerfit.member.dto.mentor.MentorHeaderResponse;
import com.careerfit.member.dto.mentor.MentorIntroductionResponse;
import com.careerfit.member.dto.mentor.MentorListPageResponse;
import com.careerfit.member.dto.mentor.MentorReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "멘토 조회 API", description = "멘토 목록 조회 및 상세 정보 API")
public interface MentorQueryControllerDocs {

    @Operation(summary = "멘토 목록 조회", description = "검색 조건과 페이징 정보를 기반으로 멘토 목록을 조회합니다.")
    ResponseEntity<ApiResponse<MentorListPageResponse>> getMentors(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String sortBy,
        @RequestParam(required = false) String sortOrder,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    );


    @Operation(summary = "멘토 헤더 조회", description = "멘토 ID로 기본 정보(헤더)를 조회합니다.")
    ResponseEntity<ApiResponse<MentorHeaderResponse>> getMentorHeader(
        @PathVariable Long mentorId
    );

    @Operation(summary = "멘토 소개 조회", description = "멘토 ID로 소개 정보를 조회합니다.")
    ResponseEntity<ApiResponse<MentorIntroductionResponse>> getMentorIntroduction(
        @PathVariable Long mentorId
    );

    @Operation(summary = "멘토 리뷰 조회", description = "멘토 ID로 리뷰 정보를 조회합니다.")
    ResponseEntity<ApiResponse<MentorReviewResponse>> getMentorReviews(
        @PathVariable Long mentorId
    );

    @Operation(summary = "멘토 대시보드 조회", description = "멘토가 받은 멘토링 요청 또는 남긴 코멘트와 관련된 문서 목록을 조회합니다. "
        + "멘토가 남긴 코멘트의 생성 시각 or 멘토링 요청 시각을 기준으로 정렬됩니다.")
    ResponseEntity<ApiResponse<List<MentorDashboardItem>>> getMentorDashboard(
        @PathVariable Long mentorId
    );
}

