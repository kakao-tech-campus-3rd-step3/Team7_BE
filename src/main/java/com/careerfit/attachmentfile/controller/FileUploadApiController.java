package com.careerfit.attachmentfile.controller;

import com.careerfit.document.domain.DocumentType;
import com.careerfit.attachmentfile.dto.PresignedUrlRequest;
import com.careerfit.attachmentfile.dto.PresignedUrlResponse;
import com.careerfit.attachmentfile.service.FileUploadService;
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
        @RequestParam(name = "document-type") DocumentType documentType,
        @Valid @RequestBody PresignedUrlRequest request) {

        PresignedUrlResponse response = fileUploadService.generatePresignedUrl(
            applicationId,
            documentType,
            request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
