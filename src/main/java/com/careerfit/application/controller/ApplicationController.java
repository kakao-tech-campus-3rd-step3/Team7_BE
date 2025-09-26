package com.careerfit.application.controller;

import com.careerfit.application.dto.*;
import com.careerfit.application.service.ApplicationCommandService;
import com.careerfit.application.service.ApplicationQueryService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationCommandService applicationCommandService;
    private final ApplicationQueryService applicationQueryService;

    @PostMapping("/analyze")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<JobPostingAnalysisResponse> analyzeJobPostingUrl(
        @RequestBody JobPostingUrlRequest request) {
        JobPostingAnalysisResponse response = applicationCommandService.analyze(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerApplication(
        @RequestBody ApplicationRegisterRequest request,
        @RequestParam Long memberId
    ) {
        applicationCommandService.registerApplication(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ApplicationListResponse> getApplicationList(@RequestParam Long memberId) {
        ApplicationListResponse response = applicationQueryService.getApplicationList(memberId);
        return ApiResponse.success(response);
    }

    @GetMapping("/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ApplicationDetailHeaderResponse> getApplicationDetailHeader(
        @PathVariable Long applicationId) {
        ApplicationDetailHeaderResponse response = applicationQueryService.getApplicationDetailHeader(
            applicationId);
        return ApiResponse.success(response);
    }

    @PatchMapping("/{applicationId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateApplicationStatus(
        @PathVariable Long applicationId,
        @RequestBody @Valid ApplicationStatusUpdateRequest request
    ) {
        applicationCommandService.updateStatus(applicationId, request);
        return ApiResponse.success();
    }

    @PatchMapping("/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateApplicationContent(
        @PathVariable Long applicationId,
        @RequestBody @Valid ApplicationContentUpdateRequest request
    ) {
        applicationCommandService.updateContent(applicationId, request);
        return ApiResponse.success();
    }

    @DeleteMapping("/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteApplication(@PathVariable Long applicationId) {
        applicationCommandService.deleteApplication(applicationId);
        return ApiResponse.success();
    }
}
