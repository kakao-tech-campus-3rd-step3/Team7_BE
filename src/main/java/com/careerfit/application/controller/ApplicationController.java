package com.careerfit.application.controller;

import com.careerfit.application.dto.ApplicationContentUpdateRequest;
import com.careerfit.application.dto.ApplicationDetailHeaderResponse;
import com.careerfit.application.dto.ApplicationListResponse;
import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.dto.ApplicationStatusUpdateRequest;
import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import com.careerfit.application.service.ApplicationCommandService;
import com.careerfit.application.service.ApplicationQueryService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController implements ApplicationControllerDocs {

    private final ApplicationCommandService applicationCommandService;
    private final ApplicationQueryService applicationQueryService;

    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<JobPostingAnalysisResponse>> analyzeJobPostingUrl(
        @RequestBody JobPostingUrlRequest request) {
        JobPostingAnalysisResponse response = applicationCommandService.analyze(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerApplication(
        @RequestBody ApplicationRegisterRequest request,
        @RequestParam Long memberId
    ) {
        applicationCommandService.register(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ApplicationListResponse>> getApplicationList(
        @RequestParam Long memberId) {
        ApplicationListResponse response = applicationQueryService.getList(memberId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<ApiResponse<ApplicationDetailHeaderResponse>> getApplicationDetailHeader(
        @PathVariable Long applicationId) {
        ApplicationDetailHeaderResponse response = applicationQueryService.getDetailHeader(
            applicationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<ApiResponse<Void>> updateApplicationStatus(
        @PathVariable Long applicationId,
        @RequestBody @Valid ApplicationStatusUpdateRequest request
    ) {
        applicationCommandService.updateStatus(applicationId, request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @PatchMapping("/{applicationId}")
    public ResponseEntity<ApiResponse<Void>> updateApplicationContent(
        @PathVariable Long applicationId,
        @RequestBody @Valid ApplicationContentUpdateRequest request
    ) {
        applicationCommandService.updateContent(applicationId, request);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<ApiResponse<Void>> deleteApplication(@PathVariable Long applicationId) {
        applicationCommandService.delete(applicationId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
