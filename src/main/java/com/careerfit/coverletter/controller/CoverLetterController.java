package com.careerfit.coverletter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerfit.coverletter.dto.CoverLetterRegisterRequest;
import com.careerfit.coverletter.service.CoverLetterService;
import com.careerfit.global.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications/{applicationId}/cover-letters")
@RequiredArgsConstructor
public class CoverLetterController {

    private final CoverLetterService coverLetterService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerCoverLetter(@PathVariable(name = "applicationId") Long applicationId,
        @Valid @RequestBody CoverLetterRegisterRequest dto){
        coverLetterService.registerCoverLetter(applicationId, dto);
        return ResponseEntity.ok(ApiResponse.success());
    }

}
