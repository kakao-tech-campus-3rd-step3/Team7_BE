package com.careerfit.AttachmentFile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.domain.AttachmentFileType;
import com.careerfit.attachmentfile.repository.AttachmentFileRepository;
import com.careerfit.attachmentfile.service.AttachmentFileCommandService;
import com.careerfit.attachmentfile.service.AttachmentFileFinder;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.global.util.DocumentUtil;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttachmentFileCommandServiceTest {

    @Mock
    private AttachmentFileRepository attachmentFileRepository;

    @Mock
    private AttachmentFileFinder attachmentFileFinder;

    @Mock
    private ApplicationFinder applicationFinder;

    @InjectMocks
    private AttachmentFileCommandService attachmentFileCommandService;

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
            .originalFileName("MyResume")
            .storedFilePath("applications/1/resumes/a42f8cd9-7391-437d-91ef-9b78e462db9c_MyResume1_MyResume")
            .title("MyResume1")
            .attachmentFileType(AttachmentFileType.RESUME)
            .build();
        app1.addDocument(file1);

        file2 = AttachmentFile.builder()
            .id(2L)
            .originalFileName("MyPortfolio")
            .storedFilePath("applications/1/portfolios/a42f8cd9-7391-437d-91ef-9b78e462db9c_MyPortfolio1_MyPortfolio")
            .title("MyPortfolio1")
            .attachmentFileType(AttachmentFileType.PORTFOLIO)
            .build();
        app2.addDocument(file2);

        file3 = AttachmentFile.builder()
            .id(3L)
            .originalFileName("MyResumeV2")
            .storedFilePath("applications/1/resumes/a42f8cd9-7391-437d-91ef-9b78e462db9c_MyResume2_MyResumeV2")
            .title("MyResume2")
            .attachmentFileType(AttachmentFileType.RESUME)
            .build();
        app1.addDocument(file3);
    }

    @Test
    @DisplayName("파일 메타 데이터 저장에 성공하면 200")
    void saveFile() {

        // uniqueFileName으로부터 applicationId, documentTitle, originalFileName 추출
        // given
        String uniqueFileName = "applications/1/resumes/a42f8cd9-7391-437d-91ef-9b78e462db9c_MyResume1_MyResume";

        // when
        Long extractedApplicationId = DocumentUtil.extractApplicationId(uniqueFileName);
        String documentTitle = DocumentUtil.extractDocumentTitle(uniqueFileName);
        String originalFileName = DocumentUtil.extractOriginalFileName(uniqueFileName);

        // then
        assertThat(extractedApplicationId).isEqualTo(1L);
        assertThat(documentTitle).isEqualTo("MyResume1");
        assertThat(originalFileName).isEqualTo("MyResume");


        // 실제 파일 메타 데이터 저장
        // given
        Long requestApplicationId = 1L;
        AttachmentFileType documentType = AttachmentFileType.RESUME;
        when(applicationFinder.getApplicationOrThrow(extractedApplicationId)).thenReturn(app1);
        ArgumentCaptor<AttachmentFile> attachmentFileCaptor = ArgumentCaptor.forClass(AttachmentFile.class);

        // when
        attachmentFileCommandService.saveFile(requestApplicationId, uniqueFileName, documentType);

        // then
        verify(attachmentFileRepository).save(attachmentFileCaptor.capture());
        AttachmentFile savedFile = attachmentFileCaptor.getValue();
        assertThat(savedFile.getApplication()).isEqualTo(app1);
        assertThat(savedFile.getStoredFilePath()).isEqualTo(uniqueFileName);
        assertThat(savedFile.getOriginalFileName()).isEqualTo(originalFileName);
        assertThat(savedFile.getTitle()).isEqualTo(documentTitle);
    }

    @Test
    @DisplayName("파일 메타 데이터 삭제에 성공하면 200")
    void deleteFile() {
        // given
        Long applicationId = 1L;
        Long attachmentFileId = 3L;
        when(attachmentFileFinder.findAttachmentFileOrThrow(attachmentFileId)).thenReturn(file3);
        doNothing().when(attachmentFileRepository).deleteById(attachmentFileId);

        // when
        attachmentFileCommandService.deleteFile(applicationId, attachmentFileId);

        // then
        verify(attachmentFileFinder, times(1)).findAttachmentFileOrThrow(attachmentFileId);
        verify(attachmentFileRepository, times(1)).deleteById(attachmentFileId);
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