package com.careerfit.attachmentfile.controller;

import com.careerfit.attachmentfile.dto.CompleteUploadRequest;
import com.careerfit.attachmentfile.dto.FileCreateResponse;
import com.careerfit.attachmentfile.dto.FileInfoResponse;
import com.careerfit.attachmentfile.service.AttachmentFileService;
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
@RequestMapping("/api/applications/{application-id}/attachment-files")
public class AttachmentFileApiController {

    private final AttachmentFileService attachmentFileService;

    // 파일 업로드 완료 처리: 파일 메타데이터 저장
    @PostMapping("/complete-upload")
    public ResponseEntity<ApiResponse<FileCreateResponse>> completeUploadPortfolio(
        @PathVariable(name = "application-id") Long applicationId,
        @Valid @RequestBody CompleteUploadRequest request
    ) {
        FileCreateResponse response = attachmentFileService.completeUpload(applicationId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("{attachment-file-id}")
    public ResponseEntity<ApiResponse<FileInfoResponse>> getPortfolioInfo(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    ) {
        FileInfoResponse response = attachmentFileService.getPortfolioInfo(applicationId, attachmentFileId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("{attachment-file-id}")
    public ResponseEntity<ApiResponse<?>> deletePortfolio(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    ) {
        attachmentFileService.deletePortfolio(applicationId, attachmentFileId);
        return ResponseEntity.ok(ApiResponse.success());
    }

}