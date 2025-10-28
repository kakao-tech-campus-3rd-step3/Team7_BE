package com.careerfit.application.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.domain.ApplicationStatus;
import com.careerfit.application.dto.ApplicationDetailHeaderResponse;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApplicationQueryService 단위 테스트")
class ApplicationQueryServiceTest {

    @InjectMocks
    private ApplicationQueryService applicationQueryService;

    @Mock
    private ApplicationJpaRepository applicationJpaRepository;

    @Nested
    @DisplayName("지원서 헤더 상세 조회(getApplicationDetailHeader) 시")
    class GetApplicationDetailHeader {

        @Test
        @DisplayName("ID에 해당하는 지원서가 존재하면 ApplicationDetailHeaderResponse를 반환한다")
        void getApplicationDetailHeader_Success() {
            // given
            Long applicationId = 1L;
            Member member = Member.builder().id(1L).build();
            Application mockApplication = Application.builder()
                .id(applicationId)
                .companyName("테스트 컴퍼니")
                .applyPosition("서버 개발자")
                .applicationStatus(ApplicationStatus.WRITING)
                .member(member)
                .build();

            given(applicationJpaRepository.findById(applicationId))
                .willReturn(Optional.of(mockApplication));

            // when
            ApplicationDetailHeaderResponse response = applicationQueryService.getDetailHeader(
                applicationId);

            // then
            assertAll(
                () -> assertThat(response.applicationId()).isEqualTo(applicationId),
                () -> assertThat(response.companyName()).isEqualTo("테스트 컴퍼니"),
                // .getValue()가 아닌 .name()으로 enum 상수 이름을 직접 비교합니다.
                () -> assertThat(response.applicationStatus()).isEqualTo(
                    ApplicationStatus.WRITING)
            );

            verify(applicationJpaRepository).findById(applicationId);
        }

        @Test
        @DisplayName("ID에 해당하는 지원서가 없으면 ApplicationException 예외를 던진다")
        void getApplicationDetailHeader_ThrowsException_WhenNotFound() {
            // given
            Long nonExistentId = 999L;

            given(applicationJpaRepository.findById(nonExistentId))
                .willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(
                () -> applicationQueryService.getDetailHeader(nonExistentId))
                .isInstanceOf(ApplicationException.class)
                .hasMessage(ApplicationErrorCode.APPLICATION_NOT_FOUND.getMessage());

            verify(applicationJpaRepository).findById(nonExistentId);
        }
    }

    /*
    요구사항 명확하게 이해하기 전까지는 전체 조회 테스트 코드 봉인
    @Nested
    @DisplayName("회원의 지원서 목록 조회(getApplicationList) 시")
    class GetApplicationList {

        @Test
        @DisplayName("해당 회원의 지원서 목록을 ApplicationListResponse로 반환한다")
        void getApplicationList_Success() {
            // given
            Long memberId = 1L;
            Member member = Member.builder().id(memberId).build();
            Application app1 = Application.builder().id(1L).companyName("회사 A")
                .applicationStatus(ApplicationStatus.PREPARING).member(member).build();
            Application app2 = Application.builder().id(2L).companyName("회사 B")
                .applicationStatus(ApplicationStatus.APPLIED).member(member).build();
            List<Application> mockApplications = List.of(app1, app2);

            given(applicationJpaRepository.findAllByMemberId(memberId)).willReturn(
                mockApplications);

            // when
            ApplicationListResponse response = applicationQueryService.getList(memberId);

            // then
            assertAll(
                () -> assertThat(response.applications()).hasSize(2),
                () -> assertThat(response.applications().get(0).companyName()).isEqualTo(
                    "회사 A"),
                () -> assertThat(response.applications().get(1).companyName()).isEqualTo("회사 B")
            );

            verify(applicationJpaRepository).findAllByMemberId(memberId);
        }
    }
     */
}