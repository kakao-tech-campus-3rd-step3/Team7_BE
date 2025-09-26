package com.careerfit.mentoring.domain;

import com.careerfit.application.domain.Application;
import com.careerfit.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MentoringTest {

    @Test
    @DisplayName("Mentoring 생성 - 정상 케이스")
    void createMentoring_success() {
        // given
        Application application = Application.builder().id(1L).build();
        Member mentor = Member.builder().id(2L).build();
        Member mentee = Member.builder().id(3L).build();
        String title = "자소서 첨삭 요청";
        String description = "부탁드립니다.";
        Long documentId = 10L;
        LocalDate dueDate = LocalDate.now().plusDays(7);

        // when
        Mentoring mentoring = Mentoring.of(application, mentor, mentee, title, description, documentId, dueDate);

        // then
        assertThat(mentoring.getApplication()).isEqualTo(application);
        assertThat(mentoring.getMento()).isEqualTo(mentor);
        assertThat(mentoring.getMentee()).isEqualTo(mentee);
        assertThat(mentoring.getTitle()).isEqualTo(title);
        assertThat(mentoring.getDescription()).isEqualTo(description);
        assertThat(mentoring.getDocumentId()).isEqualTo(documentId);
        assertThat(mentoring.getDueDate()).isEqualTo(dueDate);
        assertThat(mentoring.getMentoringStatus()).isEqualTo(MentoringStatus.PLAN_TO_APPLY);
    }

}
