package com.careerfit.document.controller;

import com.careerfit.document.domain.DocumentType;
import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.FileCreateResponse;
import com.careerfit.document.dto.PresignedUrlRequest;
import com.careerfit.document.dto.PresignedUrlResponse;
import com.careerfit.document.service.FileUploadService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{application-id}/file-upload")
public class FileUploadApiController {

    private final FileUploadService fileUploadService;

    // 파일 업로드 요청: PresignedUrl 발급.
    @PostMapping
    public ResponseEntity<ApiResponse<PresignedUrlResponse>> generatePresignedUrl(
        @PathVariable(name = "application-id") Long applicationId,
        @RequestParam(name = "documentType", required = true) DocumentType documentType,
        @Valid @RequestBody PresignedUrlRequest request) {

        PresignedUrlResponse response = fileUploadService.generatePresignedUrl(
            applicationId,
            documentType,
            request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 파일 업로드 완료 처리: 서버에 업로드 완료 여부를 전달하는 API, 여기서 파일 메타데이터를 DB에 저장.
    @PostMapping("/complete-upload")
    public ResponseEntity<ApiResponse<?>> completeUploadResume(
        @PathVariable(name = "application-id") Long applicationId,
        @RequestParam(name = "documentType", required = true) DocumentType documentType,
        @Valid @RequestBody CompleteUploadRequest request
    ) {
        FileCreateResponse response = fileUploadService.completeUploadFile(
            applicationId,
            documentType,
            request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
