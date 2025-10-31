package com.careerfit.comment.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.domain.Coordinate;
import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.comment.dto.CommentInfoResponse;
import com.careerfit.comment.dto.CommentUpdateRequest;
import com.careerfit.comment.repository.CommentRepository;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.document.domain.Document;
import com.careerfit.document.service.DocumentFinder;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import com.careerfit.member.service.MemberFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CommentCommandService 테스트")
class CommentCommandServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberFinder memberFinder;

    @Mock
    private DocumentFinder documentFinder;

    @Mock
    private CommentFinder commentFinder;

    @InjectMocks
    private CommentCommandService commentCommandService;

    private Member mentee;
    private Document document;
    private Comment comment;

    @BeforeEach
    void setUp() {
        MenteeProfile menteeProfile = MenteeProfile.of("경북대학교", "컴퓨터학부", 2026,
            List.of(MenteeWishCompany.of("카카오"), MenteeWishCompany.of("라인")),
            List.of(MenteeWishPosition.of("백엔드"), MenteeWishPosition.of("서버 개발")));

        mentee = Member.mentee("mentee1@gmail.com", "010-3333-3333", "김철수", null,
            OAuthProvider.KAKAO, "oauth_id_3", menteeProfile);

        Application application = createApplication(1L, "카카오", "2025 신입 백엔드 개발자", mentee,
            ApplicationStatus.PREPARING);

        CoverLetterItem item = CoverLetterItem.builder().question("질문1").answer("답변1").build();
        document = CoverLetter.createCoverLetter("테스트 자소서", List.of(item));
        application.addDocument(document);

        Coordinate coordinate = new Coordinate(10.0, 20.0, 30.0, 40.0);
        CommentCreateRequest request = new CommentCreateRequest("기존 댓글 내용", coordinate, 1);
        comment = Comment.of(document, mentee, request);
    }

    @Test
    @DisplayName("댓글 생성에 성공한다.")
    void createComment() {
        // given
        Long documentId = 1L;
        Long memberId = 1L;
        Coordinate coordinate = new Coordinate(10.0, 20.0, 30.0, 40.0);
        CommentCreateRequest request = new CommentCreateRequest("새로운 댓글 내용", coordinate, 1);

        when(documentFinder.findDocumentOrThrow(documentId)).thenReturn(document);
        when(memberFinder.getMemberOrThrow(memberId)).thenReturn(mentee);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        // when
        commentCommandService.createComment(documentId, memberId, request);

        // then
        verify(commentRepository).save(commentCaptor.capture());
        Comment savedComment = commentCaptor.getValue();
        assertThat(savedComment.getDocument()).isEqualTo(document);
        assertThat(savedComment.getMember()).isEqualTo(mentee);
        assertThat(savedComment.getContent()).isEqualTo(request.content());
        assertThat(savedComment.getCoordinate()).isEqualTo(request.coordinate());
    }

    @Test
    @DisplayName("댓글 수정에 성공한다.")
    void updateComment() {
        // given
        Long commentId = 1L;
        Long memberId = 1L;
        CommentUpdateRequest request = new CommentUpdateRequest("수정된 댓글 내용", null);

        when(commentFinder.findCommentOrThrow(commentId)).thenReturn(comment);

        // when
        CommentInfoResponse response = commentCommandService.updateComment(commentId, memberId,
            request);

        // then
        assertThat(response.content()).isEqualTo("수정된 댓글 내용");
        assertThat(comment.getContent()).isEqualTo("수정된 댓글 내용");
    }

    @Test
    @DisplayName("댓글 삭제에 성공한다.")
    void deleteComment() {
        // given
        Long commentId = 1L;
        Long memberId = 1L;

        when(commentFinder.findCommentOrThrow(commentId)).thenReturn(comment);

        // when
        commentCommandService.deleteComment(commentId, memberId);

        // then
        verify(commentRepository).delete(comment);
    }

    private Application createApplication(Long id, String company, String position, Member mentee,
        ApplicationStatus status) {
        return Application.builder()
            .id(id)
            .companyName(company)
            .applyPosition(position)
            .deadLine(LocalDateTime.now().plusDays(10))
            .applicationStatus(status)
            .member(mentee)
            .build();
    }
}