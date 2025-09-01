package com.careerfit.coverletter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerfit.coverletter.dto.CoverLetterDetailResponse;
import com.careerfit.coverletter.dto.CoverLetterRegisterRequest;
import com.careerfit.coverletter.service.CoverLetterCommandService;
import com.careerfit.coverletter.service.CoverLetterQueryService;
import com.careerfit.global.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications/{applicationId}/cover-letters")
@RequiredArgsConstructor
public class CoverLetterController {

    private final CoverLetterCommandService coverLetterCommandService;
    private final CoverLetterQueryService coverLetterQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerCoverLetter(@PathVariable(name = "applicationId") Long applicationId,
        @Valid @RequestBody CoverLetterRegisterRequest dto){
        coverLetterCommandService.registerCoverLetter(applicationId, dto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("{documentId}")
    public ResponseEntity<ApiResponse<Void>> deleteCoverLetter(@PathVariable(name = "documentId") Long documentId){
        coverLetterCommandService.deleteCoverLetter(documentId);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("{documentId}")
    public ResponseEntity<ApiResponse<CoverLetterDetailResponse>> getCoverLetterDetail(@PathVariable(name = "documentId") Long documentId){
        CoverLetterDetailResponse coverLetterDetail = coverLetterQueryService.getCoverLetterDetail(
            documentId);
        return ResponseEntity.ok(ApiResponse.success(coverLetterDetail));
    }

}
