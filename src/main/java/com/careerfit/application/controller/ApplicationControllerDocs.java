package com.careerfit.application.controller;

import com.careerfit.application.dto.ApplicationContentUpdateRequest;
import com.careerfit.application.dto.ApplicationDetailHeaderResponse;
import com.careerfit.application.dto.ApplicationListResponse;
import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.dto.ApplicationStatusUpdateRequest;
import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import com.careerfit.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "지원서 관리 API", description = "채용 공고 분석부터 지원서 등록, 조회, 수정, 삭제까지 지원서와 관련된 모든 기능을 다루는 API")
public interface ApplicationControllerDocs {

    @Operation(summary = "채용 공고 URL 분석", description = "채용 공고 URL을 AI 서버로 보내 주요 정보(회사명, 직무, 마감일 등)를 추출합니다.")
    @PostMapping("/analyze")
    ResponseEntity<ApiResponse<JobPostingAnalysisResponse>> analyzeJobPostingUrl(
        @RequestBody JobPostingUrlRequest request);

    @Operation(summary = "지원서 등록", description = "분석된 채용 공고 정보나 사용자가 직접 입력한 정보로 새로운 지원서를 등록합니다.")
    @PostMapping("/register")
    ResponseEntity<Void> register(
        @RequestBody ApplicationRegisterRequest request,
        @RequestParam Long memberId);

    @Operation(summary = "지원서 목록 조회", description = "특정 회원의 전체 지원서 목록을 요약하여 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<ApplicationListResponse>> getList(@RequestParam Long memberId);

    @Operation(summary = "지원서 상세 상단 정보 조회", description = "특정 지원서의 상세 페이지 상단에 표시될 주요 정보를 조회합니다.")
    @GetMapping("/{applicationId}")
    ResponseEntity<ApiResponse<ApplicationDetailHeaderResponse>> getDetailHeader(
        @PathVariable Long applicationId);

    @Operation(summary = "지원서 상태 변경", description = "지원서의 진행 상태(예: 서류 준비중, 지원 완료, 면접 진행)를 변경합니다.")
    @PatchMapping("/{applicationId}/status")
    ResponseEntity<ApiResponse<Void>> updateStatus(
        @PathVariable Long applicationId,
        @RequestBody @Valid ApplicationStatusUpdateRequest request);

    @Operation(summary = "지원서 내용 수정", description = "지원서에 등록된 회사명, 직무, 마감일 등의 내용을 수정합니다.")
    @PatchMapping("/{applicationId}")
    ResponseEntity<ApiResponse<Void>> updateContent(
        @PathVariable Long applicationId,
        @RequestBody @Valid ApplicationContentUpdateRequest request);

    @Operation(summary = "지원서 삭제", description = "특정 지원서를 삭제합니다.")
    @DeleteMapping("/{applicationId}")
    ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long applicationId);
}