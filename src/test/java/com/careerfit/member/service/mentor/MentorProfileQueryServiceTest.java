package com.careerfit.member.service.mentor;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.dto.mentor.MentorOwnProfileInfo;
import com.careerfit.member.exception.MemberErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class MentorProfileQueryServiceTest {

    @Mock
    private MentorFinder mentorFinder;

    @InjectMocks
    private MentorProfileQueryService queryService;

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
    void getMentorProfile_success() {
        when(mentorFinder.getMentorById(1L)).thenReturn(mentor);

        MentorOwnProfileInfo result = queryService.getMentorProfile(1L);

        assertThat(result.name()).isEqualTo("멘토");
        assertThat(result.company()).isEqualTo("삼성전자");
        assertThat(result.jobPosition()).isEqualTo("Backend Developer");
    }

    @Test
    void getMentorProfile_profileNotFound_throwsException() {
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

        ApplicationException ex = assertThrows(ApplicationException.class,
            () -> queryService.getMentorProfile(2L));
        assertThat(ex.getErrorCode()).isEqualTo(MemberErrorCode.MENTOR_PROFILE_NOT_FOUND);
    }
}
