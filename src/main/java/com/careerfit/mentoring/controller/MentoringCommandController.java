package com.careerfit.mentoring.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.mentoring.dto.MentoringCreateRequest;
import com.careerfit.mentoring.service.MentoringCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentoring")
@RequiredArgsConstructor
public class MentoringCommandController implements MentoringCommandControllerDocs {

    private final MentoringCommandService mentoringCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMentoring(
        @RequestBody MentoringCreateRequest dto,
        @RequestHeader("X-User-Id") Long menteeId
    ) {
        Long id = mentoringCommandService.createMentoring(dto, menteeId);
        return ResponseEntity.ok(ApiResponse.success(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMentoring(
        @PathVariable Long id,
        @RequestHeader("X-User-Id") Long menteeId
    ) {
        mentoringCommandService.deleteMentoring(id, menteeId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
