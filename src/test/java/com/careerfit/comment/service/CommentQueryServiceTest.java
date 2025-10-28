package com.careerfit.comment.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.comment.domain.Comment;
import com.careerfit.comment.domain.Coordinate;
import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.comment.dto.CommentInfoResponse;
import com.careerfit.comment.repository.CommentRepository;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.document.domain.Document;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Comment Query Service 테스트")
class CommentQueryServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentFinder commentFinder;

    @InjectMocks
    private CommentQueryService commentQueryService;

    private Member mentee;
    private Document document;
    private Comment comment1;
    private Comment comment2;

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
        document.setApplication(application);

        Coordinate coordinate1 = new Coordinate(10.0, 20.0, 30.0, 40.0);
        CommentCreateRequest request1 = new CommentCreateRequest("첫번째 댓글", coordinate1);
        comment1 = Comment.of(document, mentee, request1);

        Coordinate coordinate2 = new Coordinate(50.0, 60.0, 70.0, 80.0);
        CommentCreateRequest request2 = new CommentCreateRequest("두번째 댓글", coordinate2);
        comment2 = Comment.of(document, mentee, request2);
    }

    @Test
    @DisplayName("댓글 단건 조회에 성공한다.")
    void findComment() {
        // given
        Long commentId = 1L;
        Long memberId = 1L;
        when(commentFinder.findCommentOrThrow(commentId)).thenReturn(comment1);

        // when
        CommentInfoResponse response = commentQueryService.findComment(commentId, memberId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.content()).isEqualTo("첫번째 댓글");
        assertThat(response.writerInfo().name()).isEqualTo(mentee.getName());
    }

    @Test
    @DisplayName("문서의 댓글 목록 조회에 성공한다.")
    void findAllComment() {
        // given
        Long documentId = 1L;
        Long memberId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        List<Comment> comments = List.of(comment1, comment2);
        Page<Comment> commentPage = new PageImpl<>(comments, pageable, comments.size());

        when(commentRepository.findAllByDocumentId(documentId, pageable)).thenReturn(commentPage);

        // when
        Page<CommentInfoResponse> result = commentQueryService.findAllComment(documentId, memberId,
            pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).extracting(CommentInfoResponse::content)
            .containsExactly("첫번째 댓글", "두번째 댓글");
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