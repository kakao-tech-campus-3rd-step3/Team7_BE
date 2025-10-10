package com.careerfit.coverletter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.careerfit.coverletter.dto.CoverLetterDetailResponse;
import com.careerfit.coverletter.dto.CoverLetterInfoResponse;
import com.careerfit.coverletter.dto.CoverLetterRegisterRequest;
import com.careerfit.global.dto.ApiResponse;
import com.careerfit.global.dto.PagedResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(description = "자기소개서 API", name = "CoverLetter API")
public interface CoverLetterControllerDocs {

    public ResponseEntity<ApiResponse<Void>> registerCoverLetter(
        @PathVariable(name = "applicationId") Long applicationId,
        @Valid @RequestBody CoverLetterRegisterRequest dto);

    public ResponseEntity<ApiResponse<Void>> deleteCoverLetter(
        @PathVariable(name = "documentId") Long documentId);

    public ResponseEntity<ApiResponse<CoverLetterDetailResponse>> getCoverLetterDetail(
        @PathVariable(name = "documentId") Long documentId);

    public ResponseEntity<ApiResponse<PagedResponse<CoverLetterInfoResponse>>> getCoverLetterList(
        @PathVariable(name = "applicationId") Long applicationId,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable);

}
