package com.careerfit.comment.service;

import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.dto.CommentCreateRequest;
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

    // comment 생성
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

}
