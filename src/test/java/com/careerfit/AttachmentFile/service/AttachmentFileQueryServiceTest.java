package com.careerfit.AttachmentFile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.dto.FileInfoResponse;
import com.careerfit.attachmentfile.repository.AttachmentFileRepository;
import com.careerfit.attachmentfile.service.AttachmentFileFinder;
import com.careerfit.attachmentfile.service.AttachmentFileQueryService;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.document.domain.DocumentType;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import java.time.LocalDateTime;
import java.util.List;
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

@ExtendWith(MockitoExtension.class)
class AttachmentFileQueryServiceTest {

    @Mock
    private AttachmentFileRepository attachmentFileRepository;

    @Mock
    private AttachmentFileFinder attachmentFileFinder;

    @InjectMocks
    private AttachmentFileQueryService attachmentFileQueryService;

    private MenteeProfile menteeProfile1;
    private MenteeProfile menteeProfile2;
    private Member mentee1;
    private Member mentee2;
    private Application app1;
    private Application app2;
    private AttachmentFile file1;
    private AttachmentFile file2;
    private AttachmentFile file3;


    @BeforeEach
    void setUp() {
        menteeProfile1 = MenteeProfile.of("경북대학교", "컴퓨터학부", 2026,
            List.of(MenteeWishCompany.of("카카오"), MenteeWishCompany.of("라인")),
            List.of(MenteeWishPosition.of("백엔드"), MenteeWishPosition.of("서버 개발")));
        menteeProfile2 = MenteeProfile.of("영남대학교", "소프트웨어학과", 2027,
            List.of(MenteeWishCompany.of("당근")),
            List.of(MenteeWishPosition.of("IOS")));

        mentee1 = Member.mentee("mentee1@gmail.com", "010-3333-3333", "김철수", null,
            OAuthProvider.KAKAO, "oauth_id_3", menteeProfile1);
        mentee2 = Member.mentee("mentee2@gmail.com", "010-4444-4444", "박영희", null,
            OAuthProvider.KAKAO, "oauth_id_4", menteeProfile2);

        app1 = createApplication(1L, "카카오", "2025 신입 백엔드 개발자", mentee1,
            ApplicationStatus.PREPARING);
        app2 = createApplication(2L, "라인", "2025 신입 iOS 개발자", mentee2,
            ApplicationStatus.APPLIED);

        file1 = AttachmentFile.builder()
            .id(1L)
            .originalFileName("이력서.pdf")
            .storedFilePath("resumes/uuid-resume.pdf")
            .title("김철수 이력서")
            .attachmentFileType(DocumentType.RESUME)
            .build();
        app1.addDocument(file1);

        file2 = AttachmentFile.builder()
            .id(2L)
            .originalFileName("포트폴리오.pdf")
            .storedFilePath("portfolios/uuid-portfolio.pdf")
            .title("박영희 포트폴리오")
            .attachmentFileType(DocumentType.PORTFOLIO)
            .build();
        app2.addDocument(file2);

        file3 = AttachmentFile.builder()
            .id(3L)
            .originalFileName("이력서_수정본.pdf")
            .storedFilePath("resumes/uuid-resume-v2.pdf")
            .title("김철수 이력서 수정")
            .attachmentFileType(DocumentType.RESUME)
            .application(app1)
            .build();
        app1.addDocument(file3);
    }

    @Test
    @DisplayName("첨부파일 메타 데이터 단건 조회에 성공하면 200")
    void getFileInfo() {

        // given
        Long applicationId = 1L;
        Long attachmentFileId = 1L;
        when(attachmentFileFinder.findAttachmentFileOrThrow(applicationId)).thenReturn(file1);

        // when
        FileInfoResponse response = attachmentFileQueryService.getFileInfo(applicationId,
            attachmentFileId);

        // then
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.applicationId()).isEqualTo(1L);
        assertThat(response.originalFileName()).isEqualTo("이력서.pdf");
        assertThat(response.attachmentFileType()).isEqualTo(DocumentType.RESUME);
    }

    @Test
    @DisplayName("첨부파일 메타 데이터 리스트 조회에 성공하면 200")
    void getFileInfoList() {
        // given
        Long applicationId = 1L;
        DocumentType documentType = DocumentType.RESUME;
        Pageable pageable = PageRequest.of(0, 2);

        List<AttachmentFile> resumeFiles = app1.getDocuments().stream()
            .filter(doc -> doc instanceof AttachmentFile)
            .map(doc -> (AttachmentFile) doc)
            .filter(file -> documentType.equals(file.getAttachmentFileType()))
            .toList();
        Page<AttachmentFile> pagedResponse = new PageImpl<>(resumeFiles, pageable,
            resumeFiles.size());

        when(attachmentFileRepository.findAllByApplicationIdAndAttachmentFileType(applicationId,
            documentType, pageable))
            .thenReturn(pagedResponse);

        // when
        Page<FileInfoResponse> response = attachmentFileQueryService.getFileInfoList(applicationId,
            documentType, pageable);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getTotalElements()).isEqualTo(2);
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent()).extracting(FileInfoResponse::id)
            .containsExactlyInAnyOrder(1L, 3L);
        assertThat(response.getContent()).extracting(FileInfoResponse::attachmentFileType)
            .allMatch(type -> type.equals(documentType));
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
