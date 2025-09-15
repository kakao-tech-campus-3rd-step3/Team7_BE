package com.careerfit.comment.controller;

import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.comment.service.CommentService;
import com.careerfit.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents/{documentId}/comments")
public class CommentApiController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    // comment 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createComment(
        @PathVariable Long documentId,
        // 로그인 적용 시 @AuthenticationPrincipal로 변경 예정
        @RequestParam Long memberId,
        @RequestBody CommentCreateRequest request
    ) {
        commentCommandService.createComment(documentId, memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success());
    }

    // comment 단건 조회

    // comment 리스트 조회

    // comment 수정

    // comment 삭제

}
