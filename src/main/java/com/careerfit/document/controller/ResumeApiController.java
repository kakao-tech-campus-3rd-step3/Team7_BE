package com.careerfit.document.controller;

import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.PresignedUrlRequest;
import com.careerfit.document.dto.PresignedUrlResponse;
import com.careerfit.document.dto.ResumeCreateResponse;
import com.careerfit.document.service.ResumeService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{application-id}")
public class ResumeApiController {

    private final ResumeService resumeService;

    // 파일 업로드 요청: PresignedUrl 발급.
    @PostMapping("/resume")
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> generatePresignedUrl(
        @PathVariable(name = "application-id") Long applicationId,
        @Valid @RequestBody PresignedUrlRequest request) {

        PresignedUrlResponse response = resumeService.generatePresignedUrl(applicationId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 파일 업로드 완료 처리: 서버에 업로드 완료 여부를 전달하는 API, 여기서 파일 메타데이터를 DB에 저장.
    @PostMapping("/resume/complete-upload")
    public ResponseEntity<ApiResponse<?>> completeUploadResume(
        @PathVariable(name = "application-id") Long applicationId,
        @RequestBody CompleteUploadRequest request
    ) {
        ResumeCreateResponse response = resumeService.completeUploadFile(applicationId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}