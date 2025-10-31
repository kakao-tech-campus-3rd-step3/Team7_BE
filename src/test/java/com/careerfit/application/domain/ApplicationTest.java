package com.careerfit.application.domain;

import com.careerfit.application.dto.ApplicationContentUpdateRequest;
import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.document.domain.Document;
import com.careerfit.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Application 도메인 단위 테스트")
class ApplicationTest {

    private Member testMember;
    private ApplicationRegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        testMember = Member.builder().id(1L).build();

        registerRequest = new ApplicationRegisterRequest(
                "테스트 컴퍼니",
                "서버 개발자",
                LocalDateTime.now().plusDays(30),
                "서울시 강남구",
                "정규직",
                3,
                "https://programmers.co.kr/job_postings/12345"
        );
    }

    @Test
    @DisplayName("of 정적 팩토리 메소드는 Application 객체를 성공적으로 생성한다")
    void should_Create_Application_Successfully_When_Using_Of_Method() {
        // when
        Application application = Application.of(registerRequest, testMember);

        // then
        assertAll(
                "Application 생성 결과 검증",
                () -> assertThat(application.getCompanyName()).isEqualTo("테스트 컴퍼니"),
                () -> assertThat(application.getApplyPosition()).isEqualTo("서버 개발자"),
                // ... (이하 검증 로직은 동일)
                () -> assertThat(application.getMember()).isEqualTo(testMember),
                () -> assertThat(application.getApplicationStatus()).isEqualTo(
                        ApplicationStatus.PREPARING)
        );
    }

    @Nested
    @DisplayName("생성된 Application 객체는")
    class Context_With_Created_Application {

        private Application application;

        @BeforeEach
        void setUp() {
            application = Application.of(registerRequest, testMember);
        }

        @Test
        @DisplayName("addDocument 메소드를 통해 Document를 추가하고, 양방향 연관관계를 설정한다")
        void should_Add_Document_And_Set_Bidirectional_Relationship() {
            // given
            Document document = Document.builder().id(10L).build();

            // when
            application.addDocument(document);

            // then
            assertAll(
                    "Document 추가 결과 검증",
                    () -> assertThat(application.getDocuments()).hasSize(1),
                    () -> assertThat(application.getDocuments()).contains(document),
                    () -> assertThat(document.getApplication()).isEqualTo(application)
            );
        }

        @Test
        @DisplayName("updateStatus 메소드를 통해 지원 상태를 성공적으로 변경한다")
        void should_Update_Status_Successfully() {
            // given
            ApplicationStatus newStatus = ApplicationStatus.APPLIED;

            // when
            application.updateStatus(newStatus);

            // then
            assertThat(application.getApplicationStatus()).isEqualTo(newStatus);
        }

        @Test
        @DisplayName("updateContent 메소드를 통해 지원서 내용을 성공적으로 수정한다")
        void should_Update_Content_Successfully() {
            // given
            ApplicationContentUpdateRequest updateRequest = new ApplicationContentUpdateRequest(
                    "수정된 컴퍼니",
                    "프론트엔드 개발자",
                    LocalDateTime.now().plusDays(60),
                    "부산시 해운대구",
                    "계약직",
                    0
            );

            // when
            application.updateContent(updateRequest);

            // then
            assertAll(
                    "Application 내용 수정 결과 검증",
                    () -> assertThat(application.getCompanyName()).isEqualTo("수정된 컴퍼니"),
                    () -> assertThat(application.getApplyPosition()).isEqualTo("프론트엔드 개발자")
            );
        }
    }
}