package com.careerfit.member.service.mentor;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.dto.mentor.MentorCertificationRequest;
import com.careerfit.member.dto.mentor.MentorEducationRequest;
import com.careerfit.member.dto.mentor.MentorExpertiseRequest;
import com.careerfit.member.dto.mentor.MentorProfileUpdateRequest;
import com.careerfit.member.exception.MemberErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class MentorProfileUpdateServiceTest {

    @Mock
    private MentorFinder mentorFinder;

    @InjectMocks
    private MentorProfileUpdateService updateService;

    private Member mentor;
    private MentorProfile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        profile = MentorProfile.of(10, "삼성전자", "Backend Developer", "재직증명서",
            null, null, null, "안녕하세요", null, 4.5);

        mentor = Member.mentor(
            "mentor@test.com",
            "010-1111-1111",
            "멘토",
            null,
            null,
            "oauth123",
            profile
        );
    }

    @Test
    void updateMentorProfile_success_allFields() {
        when(mentorFinder.getMentorById(1L)).thenReturn(mentor);

        MentorProfileUpdateRequest request = new MentorProfileUpdateRequest(
            "홍길동",
            "mentor2@test.com",
            "010-2222-2222",
            null,
            12,
            "네이버",
            "Fullstack Developer",
            "재직증명서2",
            List.of(new MentorEducationRequest("서울대", "컴공", 2010, 2014)),
            List.of(new MentorCertificationRequest("AWS")),
            List.of(new MentorExpertiseRequest("Spring")),
            "안녕하세요2"
        );

        var result = updateService.updateMentorProfile(1L, request);

        assertThat(result.name()).isEqualTo("홍길동");
        assertThat(result.company()).isEqualTo("네이버");
        assertThat(result.jobPosition()).isEqualTo("Fullstack Developer");
        assertThat(result.certifications()).hasSize(1);
        assertThat(result.educations()).hasSize(1);
        assertThat(result.expertises()).hasSize(1);
        assertThat(result.introduction()).isEqualTo("안녕하세요2");
    }

    @Test
    void updateMentorProfile_profileNotFound_throwsException() {
        Member mentorWithoutProfile = Member.mentor(
            "mentor2@test.com",
            "010-0000-0000",
            "홍길동",
            null,
            null,
            "oauth999",
            null
        );
        when(mentorFinder.getMentorById(2L)).thenReturn(mentorWithoutProfile);

        MentorProfileUpdateRequest request = new MentorProfileUpdateRequest(
            null, null, null, null, null, null, null, null, null, null, null, null
        );

        ApplicationException ex = assertThrows(ApplicationException.class,
            () -> updateService.updateMentorProfile(2L, request));
        assertThat(ex.getErrorCode()).isEqualTo(MemberErrorCode.MENTOR_PROFILE_NOT_FOUND);
    }
}
