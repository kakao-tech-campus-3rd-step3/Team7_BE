package com.careerfit.portfolio.controller;

import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.FileCreateResponse;
import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.portfolio.service.PortfolioService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{application-id}/portfolios")
public class PortfolioApiController {

    private final PortfolioService portfolioService;

    // 파일 업로드 완료 처리: 파일 메타데이터 저장
    @PostMapping("/complete-upload")
    public ResponseEntity<ApiResponse<?>> completeUploadPortfolio(
        @PathVariable(name = "application-id") Long applicationId,
        @Valid @RequestBody CompleteUploadRequest request
    ) {
        FileCreateResponse response = portfolioService.completeUpload(applicationId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("{portfolio-id}")
    public ResponseEntity<ApiResponse<FileInfoResponse>> getPortfolioInfo(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "portfolio-id") Long portfolioId
    ) {
        FileInfoResponse response = portfolioService.getPortfolioInfo(applicationId, portfolioId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("{portfolio-id}")
    public ResponseEntity<ApiResponse<?>> deletePortfolio(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "portfolio-id") Long portfolioId
    ) {
        portfolioService.deletePortfolio(applicationId, portfolioId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}