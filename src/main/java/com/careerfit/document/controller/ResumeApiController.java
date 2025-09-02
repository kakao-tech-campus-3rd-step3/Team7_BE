package com.careerfit.document.controller;

import com.careerfit.document.domain.DocumentType;
import com.careerfit.document.dto.PresignedUrlRequest;
import com.careerfit.document.dto.PresignedUrlResponse;
import com.careerfit.document.service.FileUploadService;
import com.careerfit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes")
public class ResumeApiController {

    private final FileUploadService fileUploadService;

    @PostMapping("/{user-id}")
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> generatePresignedUrl(
        @PathVariable(name = "user-id") Long userId,
        @RequestBody PresignedUrlRequest request) {
        PresignedUrlResponse response = fileUploadService.generatePresignedUrl(
            userId,
            request.fileName(),
            request.fileType(),
            DocumentType.RESUME
        );
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.success(response));
    }
}