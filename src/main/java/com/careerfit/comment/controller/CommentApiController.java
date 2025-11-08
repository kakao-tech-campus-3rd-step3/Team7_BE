package com.careerfit.comment.controller;

import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.comment.dto.CommentInfoResponse;
import com.careerfit.comment.dto.CommentUpdateRequest;
import com.careerfit.comment.service.CommentCommandService;
import com.careerfit.comment.service.CommentQueryService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents/{documentId}/comments")
public class CommentApiController implements CommentApiDocs {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    // comment 생성
    @PostMapping
    @Override
    public ResponseEntity<ApiResponse<Void>> createComment(
        @PathVariable Long documentId,
        // TODO: 로그인 적용 시 @AuthenticationPrincipal로 변경 예정
        @RequestParam Long memberId,
        @Valid @RequestBody CommentCreateRequest request
    ) {
        commentCommandService.createComment(documentId, memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success());
    }

    // comment 단건 조회
    // 단건 조회/수정/삭제에는 documentId가 불필요하긴 하지만, 다른 API 경로와의 일관성을 위해 추가했습니다.
    @GetMapping("/{commentId}")
    @Override
    public ResponseEntity<ApiResponse<CommentInfoResponse>> getComment(
        @PathVariable Long documentId,
        @PathVariable Long commentId,
        // TODO: 로그인 적용 시 @AuthenticationPrincipal로 변경 예정
        @RequestParam Long memberId
    ) {
        CommentInfoResponse response = commentQueryService.findComment(commentId, memberId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // comment 리스트 조회: document에 작성된 comment 리스트 조회(member 검증 로직은 아직 추가 x)
    @GetMapping("/list")
    @Override
    public ResponseEntity<ApiResponse<List<CommentInfoResponse>>> getCommentList(
        @PathVariable Long documentId,
        // TODO: 로그인 적용 시 @AuthenticationPrincipal로 변경 예정
        @RequestParam Long memberId
    ) {
        List<CommentInfoResponse> response = commentQueryService.findAllComment(documentId,
            memberId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // comment 수정
    @PatchMapping("/{commentId}")
    @Override
    public ResponseEntity<ApiResponse<CommentInfoResponse>> updateComment(
        @PathVariable Long documentId,
        @PathVariable Long commentId,
        // TODO: 로그인 적용 시 @AuthenticationPrincipal로 변경 예정
        @RequestParam Long memberId,
        @Valid @RequestBody CommentUpdateRequest request
    ) {
        CommentInfoResponse response = commentCommandService.updateComment(commentId, memberId,
            request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // comment 삭제
    @DeleteMapping("/{commentId}")
    @Override
    public ResponseEntity<Void> deleteComment(
        @PathVariable Long documentId,
        @PathVariable Long commentId,
        // TODO: 로그인 적용 시 @AuthenticationPrincipal로 변경 예정
        @RequestParam Long memberId
    ) {
        commentCommandService.deleteComment(commentId, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }

}
