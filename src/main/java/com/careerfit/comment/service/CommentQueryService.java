package com.careerfit.comment.service;

import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.dto.CommentInfoResponse;
import com.careerfit.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentRepository commentRepository;
    private final CommentFinder commentFinder;

    public CommentInfoResponse findComment(Long commentId, Long memberId) {
        Comment comment = commentFinder.findCommentOrThrow(commentId);

        // 멘토나, 해당 문서를 소유한 멘티만 해당 코멘트를 조회할 수 있도록 검증 로직 추가 필요: 이후 추가 예정

        return CommentInfoResponse.from(comment);
    }

    public Page<CommentInfoResponse> findAllComment(Long documentId, Long memberId,
        Pageable pageable) {
        // 멘토나, 해당 문서를 소유한 멘티만 해당 코멘트 리스트를 조회할 수 있도록 검증 로직 추가 필요: 이후 추가 예정

        return commentRepository.findAllByDocumentId(documentId, pageable)
            .map(CommentInfoResponse::from);
    }
}
