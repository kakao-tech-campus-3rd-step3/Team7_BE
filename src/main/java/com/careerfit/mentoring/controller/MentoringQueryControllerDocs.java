package com.careerfit.mentoring.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.mentoring.dto.MentoringDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Tag(name = "멘토링 조회 API", description = "멘토링 조회 관련 API")
public interface MentoringQueryControllerDocs {

    @Operation(summary = "멘토링 단건 조회", description = "멘토링 ID로 상세 정보를 조회합니다.")
    ResponseEntity<ApiResponse<MentoringDetailResponse>> getMentoring(@PathVariable Long id);

    @Operation(summary = "내 멘토링 목록 조회", description = "멘티의 ID로 내가 신청한 멘토링 목록을 조회합니다.")
    ResponseEntity<ApiResponse<List<MentoringDetailResponse>>> getMyMentoring(@RequestHeader("X-User-Id") Long menteeId);
}
