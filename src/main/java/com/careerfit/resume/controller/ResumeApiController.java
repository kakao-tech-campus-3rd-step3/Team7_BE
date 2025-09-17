package com.careerfit.resume.controller;

import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.FileCreateResponse;
import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.resume.service.ResumeService;
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
@RequestMapping("/api/applications/{application-id}/resumes")
public class ResumeApiController {

    private final ResumeService resumeService;

    // 파일 업로드 완료 처리: 파일 메타데이터 저장
    @PostMapping("/complete-upload")
    public ResponseEntity<ApiResponse<?>> completeUploadPortfolio(
        @PathVariable(name = "application-id") Long applicationId,
        @Valid @RequestBody CompleteUploadRequest request
    ) {
        FileCreateResponse response = resumeService.completeUpload(applicationId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("{resume-id}")
    public ResponseEntity<ApiResponse<FileInfoResponse>> getResumeInfo(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "resume-id") Long resumeId
    ) {
        FileInfoResponse response = resumeService.getResumeInfo(applicationId, resumeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{resume-id}")
    public ResponseEntity<ApiResponse<?>> deleteResume(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "resume-id") Long resumeId
    ) {
        resumeService.deleteResume(applicationId, resumeId);
        return ResponseEntity.ok(ApiResponse.success());
    }


}