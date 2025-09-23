package com.careerfit.mentoring.service;

import com.careerfit.application.domain.Application;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.mentoring.domain.Mentoring;
import com.careerfit.mentoring.domain.MentoringStatus;
import com.careerfit.mentoring.dto.MentoringDetailResponse;
import com.careerfit.mentoring.exception.MentoringErrorCode;
import com.careerfit.mentoring.repository.MentoringJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class MentoringQueryServiceTest {

    @Mock
    private MentoringJpaRepository mentoringJpaRepository;

    @InjectMocks
    private MentoringQueryService mentoringQueryService;

    private Member mentor;
    private Member mentee;
    private Application application;
    private Mentoring mentoring;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 실제 객체 생성
        mentor = Member.builder()
            .name("멘토")
            .email("mentor@test.com")
            .phoneNumber("010-1111-1111")
            .memberRole(com.careerfit.member.domain.MemberRole.MENTOR)
            .provider(null)
            .oauthId("oauth123")
            .build();

        mentee = Member.builder()
            .name("멘티")
            .email("mentee@test.com")
            .phoneNumber("010-2222-2222")
            .memberRole(com.careerfit.member.domain.MemberRole.MENTEE)
            .provider(null)
            .oauthId("oauth456")
            .build();

        application = Application.builder()
            .id(1L)
            .build();

        mentoring = Mentoring.of(
            application,
            mentor,
            mentee,
            "멘토링 제목",
            "멘토링 설명",
            1L,
            LocalDate.now().plusDays(7)
        );
    }

    @Test
    @DisplayName("멘토링 단건 조회 성공")
    void getMentoring_success() {
        when(mentoringJpaRepository.findById(1L)).thenReturn(Optional.of(mentoring));

        MentoringDetailResponse response = mentoringQueryService.getMentoring(1L);

        assertThat(response.title()).isEqualTo("멘토링 제목");
        assertThat(response.description()).isEqualTo("멘토링 설명");
        assertThat(response.mentorName()).isEqualTo("멘토");
        assertThat(response.menteeName()).isEqualTo("멘티");
        assertThat(response.status()).isEqualTo(MentoringStatus.PLAN_TO_APPLY);
    }

    @Test
    @DisplayName("멘토링 단건 조회 실패 - 존재하지 않음")
    void getMentoring_fail_notFound() {
        when(mentoringJpaRepository.findById(1L)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class,
            () -> mentoringQueryService.getMentoring(1L));

        assertThat(exception.getErrorCode()).isEqualTo(MentoringErrorCode.MENTORING_NOT_FOUND);
    }

    @Test
    @DisplayName("멘티 기준 멘토링 리스트 조회 성공")
    void getMentoringByMentee_success() {
        when(mentoringJpaRepository.findByMenteeId(mentee.getId()))
            .thenReturn(List.of(mentoring));

        List<MentoringDetailResponse> responses = mentoringQueryService.getMentoringByMentee(mentee.getId());

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).menteeName()).isEqualTo("멘티");
        assertThat(responses.get(0).mentorName()).isEqualTo("멘토");
    }
}

