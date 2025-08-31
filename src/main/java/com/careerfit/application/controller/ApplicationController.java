package com.careerfit.application.controller;

import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import com.careerfit.application.service.ApplicationService;
import com.careerfit.auth.domain.CustomUserDetails;
import com.careerfit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/analyze")
    public ApiResponse<JobPostingAnalysisResponse> analyzeJobPostingUrl(
            @RequestBody JobPostingUrlRequest request) {
        JobPostingAnalysisResponse response = applicationService.analyze(request);
        return ApiResponse.success(response);
    }

    @PostMapping("/register")
    public ApiResponse<Void> registerApplication(
            @RequestBody ApplicationRegisterRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long memberId = userDetails.getUserId();
        applicationService.registerApplication(request, memberId);
        return ApiResponse.success();
    }
}
