package com.careerfit.comment.controller;

import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.comment.dto.CommentInfoResponse;
import com.careerfit.comment.dto.CommentUpdateRequest;
import com.careerfit.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Comment API", description = "댓글 API 명세서")
public interface CommentApiDocs {

    @Operation(
        summary = "댓글 생성",
        description = "특정 문서에 댓글을 생성합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "댓글 내용",
            required = true
        )
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description =
            "댓글 생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
            "문서 또는 회원을 찾을 수 없습니다.")
    })
    ResponseEntity<ApiResponse<Void>> createComment(
        @Parameter(description = "문서 ID", example = "1")
        @PathVariable Long documentId,

        @Parameter(description = "회원 ID: 로그인 적용 시 @AuthenticationPrincipal로 변경할 예정", example = "1")
        @RequestParam Long memberId,

        @Valid @RequestBody CommentCreateRequest request
    );

    @Operation(
        summary = "댓글 단건 조회",
        description = "특정 댓글의 정보를 조회합니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =
            "댓글 조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
            "댓글을 찾을 수 없습니다.")
    })
    ResponseEntity<ApiResponse<CommentInfoResponse>> getComment(
        @Parameter(description = "문서 ID", example = "1")
        @PathVariable Long documentId,

        @Parameter(description = "댓글 ID", example = "1")
        @PathVariable Long commentId,

        @Parameter(description = "회원 ID: 로그인 적용 시 @AuthenticationPrincipal로 변경할 예정", example = "1")
        @RequestParam Long memberId
    );

    @Operation(
        summary = "댓글 목록 조회",
        description = "특정 문서에 달린 댓글 목록을 조회합니다.(페이지네이션을 적용하지 않았습니다)"
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =
            "댓글 목록 조회 성공")
    })
    ResponseEntity<ApiResponse<List<CommentInfoResponse>>> getCommentList(
        @Parameter(description = "문서 ID", example = "1")
        @PathVariable Long documentId,

        @Parameter(description = "회원 ID: 로그인 적용 시 @AuthenticationPrincipal로 변경할 예정", example = "1")
        @RequestParam Long memberId
    );

    @Operation(
        summary = "댓글 수정",
        description = "특정 댓글의 내용을 수정합니다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "수정할 댓글 내용",
            required = true
        )
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =
            "댓글 수정 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
            "댓글을 찾을 수 없습니다.")
    })
    ResponseEntity<ApiResponse<CommentInfoResponse>> updateComment(
        @Parameter(description = "문서 ID", example = "1")
        @PathVariable Long documentId,

        @Parameter(description = "댓글 ID", example = "1")
        @PathVariable Long commentId,

        @Parameter(description = "회원 ID: 로그인 적용 시 @AuthenticationPrincipal로 변경할 예정", example = "1")
        @RequestParam Long memberId,

        @Valid @RequestBody CommentUpdateRequest request
    );

    @Operation(
        summary = "댓글 삭제",
        description = "특정 댓글을 삭제합니다."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description =
            "댓글 삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description =
            "댓글을 찾을 수 없습니다.")
    })
    ResponseEntity<Void> deleteComment(
        @Parameter(description = "문서 ID", example = "1")
        @PathVariable Long documentId,

        @Parameter(description = "댓글 ID", example = "1")
        @PathVariable Long commentId,

        @Parameter(description = "회원 ID: 로그인 적용 시 @AuthenticationPrincipal로 변경할 예정", example = "1")
        @RequestParam Long memberId
    );
}
