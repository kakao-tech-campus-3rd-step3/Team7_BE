package com.careerfit.mentoring.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.mentoring.dto.MentoringCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "멘토링 생성/삭제 API", description = "멘토링 생성 및 삭제 관련 API")
public interface MentoringCommandControllerDocs {

    @Operation(summary = "멘토링 생성", description = "멘티가 멘토링을 생성합니다.")
    ResponseEntity<ApiResponse<Long>> createMentoring(MentoringCreateRequest dto, Long menteeId);

    @Operation(summary = "멘토링 삭제", description = "멘티가 멘토링을 삭제합니다.")
    ResponseEntity<ApiResponse<Void>> deleteMentoring(Long id, Long menteeId);
}