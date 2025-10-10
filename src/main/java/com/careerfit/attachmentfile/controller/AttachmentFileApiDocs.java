package com.careerfit.attachmentfile.controller;

import com.careerfit.attachmentfile.domain.AttachmentFileType;
import com.careerfit.attachmentfile.dto.FileInfoResponse;
import com.careerfit.attachmentfile.dto.FileUploadRequest;
import com.careerfit.attachmentfile.dto.GetPresignedUrlResponse;
import com.careerfit.attachmentfile.dto.PutPresignedUrlResponse;
import com.careerfit.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "AttachmentFile API", description = "첨부파일(이력서/포트폴리오) API 명세서")
public interface AttachmentFileApiDocs {

    @Operation(
        summary = "파일 업로드용 url 발급",
        description = "S3에 파일을 직접 업로드할 때 필요한 임시 URL을 발급합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "파일의 원본이름과 DB에 저장할 이름, 파일 타입 정보를 담은 객체",
            required = true
        )
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "presignedUrl 발급 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터")
    })
    ResponseEntity<ApiResponse<PutPresignedUrlResponse>> generatePostPresignedUrl(
        @Parameter(description = "지원항목 ID", example = "1")
        @PathVariable(name = "application-id") Long applicationId,

        @Parameter(description = "첨부파일 종류 (RESUME or PORTFOLIO)", example = "RESUME")
        @RequestParam(name = "attachment-file-type") AttachmentFileType attachmentFileType,

        @Parameter
        @Valid @RequestBody FileUploadRequest request
    );


    @Operation(
        summary = "파일 조회용 url 발급",
        description = "S3에 업로드된 파일을 조회할 때 필요한 임시 URL을 발급합니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "presignedUrl 발급 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 파일을 찾을 수 없습니다.")
    })
    ResponseEntity<ApiResponse<GetPresignedUrlResponse>> generateGetPresignedUrl(
        @Parameter(description = "지원항목 ID", example = "1")
        @PathVariable(name = "application-id") Long applicationId,

        @Parameter(description = "첨부파일 ID", example = "1")
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    );


    @Operation(
        summary = "파일 삭제",
        description = "S3에 업로드된 파일과 DB 메타데이터를 삭제합니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "파일 삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 파일을 찾을 수 없습니다.")
    })
    ResponseEntity<ApiResponse<Void>> deleteFile(
        @Parameter(description = "지원항목 ID", example = "1")
        @PathVariable(name = "application-id") Long applicationId,

        @Parameter(description = "첨부파일 ID", example = "1")
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    );

    @Operation(
        summary = "파일 메타데이터 단건 조회",
        description = "첨부파일의 메타데이터(원본 파일명, 파일 크기 등)를 조회합니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "메타데이터 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 파일을 찾을 수 없습니다.")
    })
    ResponseEntity<ApiResponse<FileInfoResponse>> getFileMetaData(
        @Parameter(description = "지원항목 ID", example = "1")
        @PathVariable(name = "application-id") Long applicationId,

        @Parameter(description = "첨부파일 ID", example = "1")
        @PathVariable(name = "attachment-file-id") Long attachmentFileId
    );

    @Operation(
        summary = "파일 메타데이터 목록 조회",
        description = "특정 지원 항목 및 파일 종류에 해당하는 첨부파일 메타데이터 목록을 조회합니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "메타데이터 목록 조회 성공")
    })
    ResponseEntity<ApiResponse<Page<FileInfoResponse>>> getFileMetaDataList(
        @Parameter(description = "지원항목 ID", example = "1")
        @PathVariable(name = "application-id") Long applicationId,

        @Parameter(description = "첨부파일 종류 (RESUME or PORTFOLIO)", example = "RESUME")
        @RequestParam(name = "attachment-file-type") AttachmentFileType attachmentFileType,

        Pageable pageable
    );
}
