package com.careerfit.document.controller;

import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.document.service.PortfolioService;
import com.careerfit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{application-id}/portfolios")
public class PortfolioApiController {

    private final PortfolioService portfolioService;

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