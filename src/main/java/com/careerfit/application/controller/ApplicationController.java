package com.careerfit.application.controller;

import com.careerfit.application.dto.ApplicationDetailHeaderResponse;
import com.careerfit.application.dto.ApplicationListResponse;
import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import com.careerfit.application.service.ApplicationQueryService;
import com.careerfit.application.service.ApplicationService;
import com.careerfit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationQueryService applicationQueryService;

    @PostMapping("/analyze")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<JobPostingAnalysisResponse> analyzeJobPostingUrl(
            @RequestBody JobPostingUrlRequest request) {
        JobPostingAnalysisResponse response = applicationService.analyze(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerApplication(
            @RequestBody ApplicationRegisterRequest request,
            @RequestParam Long memberId
    ) {
        applicationService.registerApplication(request, memberId);
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
}
