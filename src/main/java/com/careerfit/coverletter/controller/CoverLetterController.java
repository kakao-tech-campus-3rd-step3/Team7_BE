package com.careerfit.coverletter.controller;

import com.careerfit.coverletter.dto.CoverLetterDetailResponse;
import com.careerfit.coverletter.dto.CoverLetterInfoResponse;
import com.careerfit.coverletter.dto.CoverLetterRegisterRequest;
import com.careerfit.coverletter.service.CoverLetterCommandService;
import com.careerfit.coverletter.service.CoverLetterQueryService;
import com.careerfit.global.dto.ApiResponse;
import com.careerfit.global.dto.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications/{applicationId}/cover-letters")
@RequiredArgsConstructor
public class CoverLetterController implements CoverLetterControllerDocs {

    private final CoverLetterCommandService coverLetterCommandService;
    private final CoverLetterQueryService coverLetterQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerCoverLetter(
        @PathVariable(name = "applicationId") Long applicationId,
        @Valid @RequestBody CoverLetterRegisterRequest dto) {
        coverLetterCommandService.registerCoverLetter(applicationId, dto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("{documentId}")
    public ResponseEntity<ApiResponse<Void>> deleteCoverLetter(
        @PathVariable(name = "documentId") Long documentId) {
        coverLetterCommandService.deleteCoverLetter(documentId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("{documentId}")
    public ResponseEntity<ApiResponse<CoverLetterDetailResponse>> getCoverLetterDetail(
        @PathVariable(name = "documentId") Long documentId) {
        CoverLetterDetailResponse coverLetterDetail = coverLetterQueryService.getCoverLetterDetail(
            documentId);
        return ResponseEntity.ok(ApiResponse.success(coverLetterDetail));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<CoverLetterInfoResponse>>> getCoverLetterList(
        @PathVariable(name = "applicationId") Long applicationId,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        PagedResponse<CoverLetterInfoResponse> response = coverLetterQueryService.getCoverLetterList(
            applicationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
