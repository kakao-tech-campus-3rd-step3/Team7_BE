package com.careerfit.comment.service;

import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.comment.dto.CommentInfoResponse;
import com.careerfit.comment.dto.CommentUpdateRequest;
import com.careerfit.comment.repository.CommentRepository;
import com.careerfit.document.domain.Document;
import com.careerfit.document.service.DocumentFinder;
import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final DocumentFinder documentFinder;
    private final MemberFinder memberFinder;
    private final CommentFinder commentFinder;

    @Transactional
    public void createComment(
        Long documentId,
        Long memberId,
        CommentCreateRequest request
    ) {
        Document document = documentFinder.findDocumentOrThrow(documentId);
        Member member = memberFinder.getMemberOrThrow(memberId);
        Comment comment = Comment.of(document, member, request);

        commentRepository.save(comment);
    }

    @Transactional
    public CommentInfoResponse updateComment(
        Long commentId,
        Long memberId,
        CommentUpdateRequest request
    ) {
        Comment comment = commentFinder.findCommentOrThrow(commentId);
        comment.updateContent(request.content(), request.page());
        return CommentInfoResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        // 멘토나, 해당 문서를 소유한 멘티만 해당 코멘트 리스트를 조회할 수 있도록 검증 로직 추가 필요: 이후 추가 예정
        Comment comment = commentFinder.findCommentOrThrow(commentId);
        commentRepository.delete(comment);
    }
}


