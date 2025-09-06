package com.careerfit.application.controller;

import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import com.careerfit.application.service.ApplicationService;
import com.careerfit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

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
}
