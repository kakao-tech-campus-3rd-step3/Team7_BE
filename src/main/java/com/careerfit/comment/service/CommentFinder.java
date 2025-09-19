package com.careerfit.comment.service;

import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.exception.CommentErrorCode;
import com.careerfit.comment.repository.CommentRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentFinder {

    private final CommentRepository commentRepository;

    public Comment findCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new ApplicationException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    // 문서 주인(멘티)와 모든 멘토가 볼 수 있도록 할 건지, 코멘트를 남긴 멘토만 볼 수 있도록 할 건지
    // 비즈니스 로직에 대해 팀원들과 합의가 필요한 상황이라 이 메서드는 일단 사용하지 않겠습니다.
    // 이후에 검증 로직을 추가하는 걸로 할게요.
    public Comment findOwnedCommentOrThrow(Long commentId, Long memberId) {
        Comment comment = findCommentOrThrow(commentId);
        if (!comment.getMember().getId().equals(memberId)) {
            throw new ApplicationException(CommentErrorCode.NOT_ALLOWED_TO_QUERY);
        }
        return comment;
    }
}
