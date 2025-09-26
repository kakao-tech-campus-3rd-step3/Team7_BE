package com.careerfit.mentoring.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.auth.exception.AuthErrorCode;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import com.careerfit.mentoring.domain.Mentoring;
import com.careerfit.mentoring.dto.MentoringCreateRequest;
import com.careerfit.mentoring.exception.MentoringErrorCode;
import com.careerfit.mentoring.repository.MentoringJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MentoringCommandServiceTest {

    private final MentoringJpaRepository mentoringJpaRepository = mock(MentoringJpaRepository.class);
    private final MemberFinder memberFinder = mock(MemberFinder.class);
    private final ApplicationFinder applicationFinder = mock(ApplicationFinder.class);
    private final MentoringCommandService service = new MentoringCommandService(
        mentoringJpaRepository, memberFinder, applicationFinder
    );

    @Test
    @DisplayName("멘토링 생성 성공")
    void createMentoring_success() {
        // given
        Long mentorId = 1L;
        Long menteeId = 2L;
        Long applicationId = 3L;

        MentoringCreateRequest request = new MentoringCreateRequest(
            mentorId,
            applicationId,
            "자소서 첨삭 요청",
            LocalDate.now().plusDays(7),
            "첨삭 부탁드립니다."
        );

        Member mentor = mock(Member.class);
        Member mentee = mock(Member.class);
        Application application = mock(Application.class);
        Mentoring savedMentoring = Mentoring.of(application, mentor, mentee,
            request.title(), request.description(), request.documentId(), request.dueDate());

        when(memberFinder.getMentorOrThrow(mentorId)).thenReturn(mentor);
        when(memberFinder.getMenteeOrThrow(menteeId)).thenReturn(mentee);
        when(applicationFinder.getApplicationOrThrow(applicationId)).thenReturn(application);
        when(mentoringJpaRepository.save(any(Mentoring.class))).thenReturn(savedMentoring);

        // when
        Long resultId = service.createMentoring(request, menteeId);

        // then
        assertThat(resultId).isEqualTo(savedMentoring.getId());
        verify(mentoringJpaRepository).save(any(Mentoring.class));
    }

    @Test
    @DisplayName("멘토링 삭제 실패 - 권한 없음")
    void deleteMentoring_fail_unauthorized() {
        // given
        Long mentoringId = 1L;
        Long menteeId = 2L;

        Member mentee = mock(Member.class);
        when(mentee.getId()).thenReturn(99L);

        Mentoring mentoring = mock(Mentoring.class);
        when(mentoring.getMentee()).thenReturn(mentee);
        when(mentoringJpaRepository.findById(mentoringId)).thenReturn(Optional.of(mentoring));

        // when & then
        ApplicationException ex = assertThrows(ApplicationException.class, () ->
            service.deleteMentoring(mentoringId, menteeId)
        );

        assertThat(ex.getErrorCode()).isEqualTo(AuthErrorCode.ACCESS_DENIED);
    }

    @Test
    @DisplayName("멘토링 삭제 성공")
    void deleteMentoring_success() {
        // given
        Long mentoringId = 1L;
        Long menteeId = 2L;

        Member mentee = mock(Member.class);
        when(mentee.getId()).thenReturn(menteeId);

        Mentoring mentoring = mock(Mentoring.class);
        when(mentoring.getMentee()).thenReturn(mentee);

        when(mentoringJpaRepository.findById(mentoringId)).thenReturn(Optional.of(mentoring));

        // when
        service.deleteMentoring(mentoringId, menteeId);

        // then
        verify(mentoringJpaRepository).delete(mentoring);
    }
}
