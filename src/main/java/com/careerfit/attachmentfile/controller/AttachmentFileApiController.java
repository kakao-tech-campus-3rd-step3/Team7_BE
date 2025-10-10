package com.careerfit.attachmentfile.controller;

import com.careerfit.attachmentfile.dto.FileInfoResponse;
import com.careerfit.attachmentfile.dto.GetPresignedUrlResponse;
import com.careerfit.attachmentfile.dto.FileUploadRequest;
import com.careerfit.attachmentfile.dto.PutPresignedUrlResponse;
import com.careerfit.attachmentfile.service.AttachmentFileCommandService;
import com.careerfit.attachmentfile.service.AttachmentFileQueryService;
import com.careerfit.attachmentfile.service.S3CommandService;
import com.careerfit.attachmentfile.service.S3QueryService;
import com.careerfit.document.domain.DocumentType;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{application-id}/attachment-files")
public class AttachmentFileApiController {

    private final S3QueryService s3QueryService;
    private final S3CommandService s3CommandService;
    private final AttachmentFileQueryService attachmentFileQueryService;
    private final AttachmentFileCommandService attachmentFileCommandService;

    /////* S3에 저장된 실제 파일 API */////

    // 파일 업로드
    @PostMapping("/file-upload")
    public ResponseEntity<ApiResponse<PutPresignedUrlResponse>> generatePostPresignedUrl(
        @PathVariable(name = "application-id") Long applicationId,
        @RequestParam(name = "document-type") DocumentType documentType,
        @Valid @RequestBody FileUploadRequest request
    ) {
        PutPresignedUrlResponse response = s3CommandService.generatePutPresignedUrl(applicationId,
            documentType, request);
        attachmentFileCommandService.saveFile(applicationId, response.uniqueFileName(), documentType);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 파일 조회
    @GetMapping("/{attachment-file-id}")
    public ResponseEntity<ApiResponse<GetPresignedUrlResponse>> generateGetPresignedUrl(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    ) {
        GetPresignedUrlResponse response = s3QueryService.generateGetPresignedUrl(applicationId,
            attachmentFileId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 파일 삭제
    @DeleteMapping("/{attachment-file-id}")
    public ResponseEntity<ApiResponse<Void>> deleteFile(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    ) {
        s3CommandService.deleteFile(applicationId, attachmentFileId);
        attachmentFileCommandService.deleteFile(applicationId, attachmentFileId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(ApiResponse.success());
    }

    /////* 파일 메타 데이터 조회 API */////

    // 파일 메타 데이터 단건 조회
    @GetMapping("/{attachment-file-id}/metadata")
    public ResponseEntity<ApiResponse<FileInfoResponse>> getFileMetaData(
        @PathVariable(name = "application-id") Long applicationId,
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    ) {
        FileInfoResponse response = attachmentFileQueryService.getFileInfo(applicationId,
            attachmentFileId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 파일 메타 데이터 리스트 조회
    @GetMapping("/metadata/list")
    public ResponseEntity<ApiResponse<List<FileInfoResponse>>> getFileMetaDataList(
        @PathVariable(name = "application-id") Long applicationId,
        @RequestParam(name = "document-type") DocumentType documentType
    ) {
        List<FileInfoResponse> response = attachmentFileQueryService.getFileInfoList(applicationId,
            documentType);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}