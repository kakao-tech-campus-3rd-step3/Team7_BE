package com.careerfit.member.service.mentee;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.service.MemberFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class MenteeProfileTestQueryServiceTest {

    @Mock
    private MemberFinder memberFinder;

    @InjectMocks
    private MenteeProfileQueryService queryService;

    private Member mentee;
    private MenteeProfile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        profile = MenteeProfile.of("서울대", "컴퓨터공학", 2025, null, null);
        mentee = Member.mentee(
            "mentee@test.com",
            "010-2222-2222",
            "멘티",
            null,
            null,
            "oauth456",
            profile
        );
    }

    @Test
    void getMenteeProfile_success() {
        when(memberFinder.getMenteeOrThrow(1L)).thenReturn(mentee);

        MenteeProfileInfo result = queryService.getMenteeProfile(1L);

        assertThat(result.university()).isEqualTo("서울대");
        assertThat(result.major()).isEqualTo("컴퓨터공학");
    }

    @Test
    void getMenteeProfile_profileNotFound_throwsException() {
        Member menteeWithoutProfile = Member.mentee(
            "mentee2@test.com",
            "010-0000-0000",
            "홍길동",
            null,
            null,
            "oauth789",
            null
        );

        when(memberFinder.getMenteeOrThrow(2L)).thenReturn(menteeWithoutProfile);

        ApplicationException ex = assertThrows(ApplicationException.class,
            () -> queryService.getMenteeProfile(2L));
        assertThat(ex.getErrorCode()).isEqualTo(MemberErrorCode.MENTEE_PROFILE_NOT_FOUND);
    }
}

