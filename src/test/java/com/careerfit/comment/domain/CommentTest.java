package com.careerfit.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.comment.dto.CommentCreateRequest;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.document.domain.Document;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    private Member mentee;
    private Application application;
    private Document document;
    private CommentCreateRequest commentCreateRequest;

    @BeforeEach
    void setUp() {
        MenteeProfile menteeProfile = MenteeProfile.of("경북대학교", "컴퓨터학부", 2026,
            List.of(MenteeWishCompany.of("카카오"), MenteeWishCompany.of("라인")),
            List.of(MenteeWishPosition.of("백엔드"), MenteeWishPosition.of("서버 개발")));

        mentee = Member.mentee("mentee1@gmail.com", "010-3333-3333", "김철수", null,
            OAuthProvider.KAKAO, "oauth_id_3", menteeProfile);

        application = createApplication(1L, "카카오", "2025 신입 백엔드 개발자", mentee,
            ApplicationStatus.PREPARING);

        CoverLetterItem item = CoverLetterItem.builder().question("질문1").answer("답변1").build();
        document = CoverLetter.createCoverLetter("테스트 자소서", List.of(item));
        application.addDocument(document);

        Coordinate coordinate = new Coordinate(10.0, 20.0, 30.0, 40.0);
        commentCreateRequest = new CommentCreateRequest("테스트 댓글 내용입니다.", coordinate);
    }

    @Test
    @DisplayName("유효한 인자가 주어지면 Comment 객체를 생성한다.")
    void success() {
        // when
        Comment comment = Comment.of(document, mentee, commentCreateRequest);

        // then
        assertAll(
            () -> assertThat(comment).isNotNull(),
            () -> assertThat(comment.getContent()).isEqualTo(commentCreateRequest.content()),
            () -> assertThat(comment.getCoordinate()).isEqualTo(commentCreateRequest.coordinate()),
            () -> assertThat(comment.getMember()).isEqualTo(mentee),
            () -> assertThat(comment.getDocument()).isEqualTo(document)
        );
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
