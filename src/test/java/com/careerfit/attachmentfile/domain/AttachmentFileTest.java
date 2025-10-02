package com.careerfit.attachmentfile.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.attachmentfile.exception.AttachmentFileErrorCode;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class AttachmentFileTest {

    private MenteeProfile menteeProfile;
    private Member mentee;
    private Application application;

    private String originalFileName;
    private String storedFilePath;
    private String documentTitle;
    private AttachmentFileType attachmentFileType;

    @BeforeEach
    void setUp() {
        originalFileName = "my_resume.pdf";
        storedFilePath = "/files/my_resume_stored.pdf";
        documentTitle = "내 이력서";
        attachmentFileType = AttachmentFileType.RESUME;

        menteeProfile = MenteeProfile.of("경북대학교", "컴퓨터학부", 2026,
            List.of(MenteeWishCompany.of("카카오"), MenteeWishCompany.of("라인")),
            List.of(MenteeWishPosition.of("백엔드"), MenteeWishPosition.of("서버 개발")));
        mentee = Member.mentee("mentee1@gmail.com", "010-3333-3333", "김철수", null,
            OAuthProvider.KAKAO, "oauth_id_3", menteeProfile);
        application = createApplication(1L, "카카오", "2025 신입 백엔드 개발자", mentee,
            ApplicationStatus.PREPARING);
    }


    @Test
    @DisplayName("유효한 인자가 주어지면 AttachmentFile 객체를 생성한다.")
    void success() {
        // given
        // setUp() 그대로 사용

        // when
        AttachmentFile attachmentFile = AttachmentFile.of(originalFileName, storedFilePath,
            documentTitle, application, attachmentFileType);

        // then
        assertAll(
            () -> assertThat(attachmentFile).isNotNull(),
            () -> assertThat(attachmentFile.getOriginalFileName()).isEqualTo(originalFileName),
            () -> assertThat(attachmentFile.getStoredFilePath()).isEqualTo(storedFilePath),
            () -> assertThat(attachmentFile.getTitle()).isEqualTo(documentTitle),
            () -> assertThat(attachmentFile.getApplication()).isEqualTo(application),
            () -> assertThat(attachmentFile.getAttachmentFileType()).isEqualTo(attachmentFileType)
        );
    }

    @Test
    @DisplayName("원본파일명이 null이거나 blank면 예외가 발생한다.")
    void 원본파일명이_null_blank면_예외발생() {
        // given
        originalFileName = null;

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(AttachmentFileErrorCode.INVALID_ORIGINAL_FILENAME);

        // given
        originalFileName = "";

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(AttachmentFileErrorCode.INVALID_ORIGINAL_FILENAME);

    }

    @Test
    @DisplayName("저장파일경로가 null이거나 blank면 예외가 발생한다.")
    void 저장파일경로_null_blank면_예외발생() {
        // given
        storedFilePath = null;

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(AttachmentFileErrorCode.INVALID_STORED_FILE_PATH);

        // given
        storedFilePath = "";

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(AttachmentFileErrorCode.INVALID_STORED_FILE_PATH);
    }

    @Test
    @DisplayName("저장 문서명이 null이거나 blank면 예외가 발생한다.")
    void 저장문서명_null_blank면_예외발생() {
        //given
        documentTitle = null;

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(AttachmentFileErrorCode.INVALID_DOCUMENT_TITLE);

        //given
        documentTitle = "";

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(AttachmentFileErrorCode.INVALID_DOCUMENT_TITLE);
    }

    @Test
    @DisplayName("지원항목이 null이면 예외가 발생한다.")
    void 지원항목_null_예외발생() {
        // given
        application = null;

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(ApplicationErrorCode.APPLICATION_NOT_FOUND);
    }

    @Test
    @DisplayName("첨부파일 타입이 null이면 예외가 발생한다.")
    void nullAttachmentFileType() {
        // given
        AttachmentFileType attachmentFileType = null;

        // when + then
        assertThatThrownBy(
            () -> AttachmentFile.of(originalFileName, storedFilePath, documentTitle,
                application, attachmentFileType))
            .isInstanceOf(ApplicationException.class)
            .extracting("errorCode")
            .isEqualTo(AttachmentFileErrorCode.INVALID_ATTACHMENT_FILE_TYPE);
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