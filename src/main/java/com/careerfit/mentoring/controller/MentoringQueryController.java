package com.careerfit.mentoring.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.mentoring.dto.MentoringDetailResponse;
import com.careerfit.mentoring.service.MentoringQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentoring")
@RequiredArgsConstructor
public class MentoringQueryController {

    private final MentoringQueryService mentoringQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MentoringDetailResponse>> getMentoring(@PathVariable Long id) {
        MentoringDetailResponse result = mentoringQueryService.getMentoring(id);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<MentoringDetailResponse>>> getMyMentorings(
        @RequestHeader("X-User-Id") Long menteeId
    ) {
        List<MentoringDetailResponse> result = mentoringQueryService.getMentoringByMentee(menteeId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

