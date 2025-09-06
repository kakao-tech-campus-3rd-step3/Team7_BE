package com.careerfit.document.controller;

import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.document.service.ResumeService;
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
@RequestMapping("/api/applications/{application-id}/resumes")
public class ResumeApiController {

    private final ResumeService resumeService;

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