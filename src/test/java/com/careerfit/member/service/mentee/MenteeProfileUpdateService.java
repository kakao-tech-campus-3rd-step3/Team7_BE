package com.careerfit.member.service.mentee;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.dto.mentee.MenteeProfileUpdateRequest;
import com.careerfit.member.dto.mentee.MenteeWishCompanyRequest;
import com.careerfit.member.dto.mentee.MenteeWishPositionRequest;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.service.MemberFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class MenteeProfileTestUpdateServiceTest {

    @Mock
    private MemberFinder memberFinder;

    @InjectMocks
    private MenteeProfileUpdateService updateService;

    private Member mentee;
    private MenteeProfile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profile = MenteeProfile.of("서울대", "컴퓨터공학", 2025, null, null);
        mentee = Member.mentee(
            "mentee1@gmail.com",
            "010-1234-5678",
            "김철수",
            null,
            null,
            "oauthId123",
            profile
        );
    }

    @Test
    void updateMenteeProfile_success_allFields() {
        when(memberFinder.getMenteeOrThrow(1L)).thenReturn(mentee);

        MenteeProfileUpdateRequest request = new MenteeProfileUpdateRequest(
            "김영희",
            "mentee1@gmail.com",
            "010-5678-1234",
            "연세대",
            "소프트웨어학과",
            2026,
            List.of(new MenteeWishCompanyRequest("삼성전자")),
            List.of(new MenteeWishPositionRequest("백엔드"))
        );

        MenteeProfileInfo result = updateService.updateMenteeProfile(1L, request);

        assertThat(result.name()).isEqualTo("김영희");
        assertThat(result.email()).isEqualTo("mentee1@gmail.com");
        assertThat(result.phoneNumber()).isEqualTo("010-5678-1234");
        assertThat(result.university()).isEqualTo("연세대");
        assertThat(result.major()).isEqualTo("소프트웨어학과");
        assertThat(result.graduationYear()).isEqualTo(2026);
        assertThat(result.wishCompanies()).hasSize(1);
        assertThat(result.wishCompanies().get(0).companyName()).isEqualTo("삼성전자");
        assertThat(result.wishPositions()).hasSize(1);
        assertThat(result.wishPositions().get(0).positionName()).isEqualTo("백엔드");
    }

    @Test
    void updateMenteeProfile_partialNullFields_keepsOldValues() {
        when(memberFinder.getMenteeOrThrow(1L)).thenReturn(mentee);

        MenteeProfileUpdateRequest request = new MenteeProfileUpdateRequest(
            null, null, null, null, null, null, null, null
        );

        MenteeProfileInfo result = updateService.updateMenteeProfile(1L, request);

        assertThat(result.name()).isEqualTo("김철수");
        assertThat(result.university()).isEqualTo("서울대");
        assertThat(result.wishCompanies()).isEmpty();
        assertThat(result.wishPositions()).isEmpty();
    }

    @Test
    void updateMenteeProfile_profileNotFound_throwsException() {
        Member menteeWithoutProfile = Member.mentee(
            "mentee2@gmail.com",
            "010-0000-0000",
            "홍길동",
            null,
            null,
            "oauthId456",
            null
        );

        when(memberFinder.getMenteeOrThrow(2L)).thenReturn(menteeWithoutProfile);

        MenteeProfileUpdateRequest request = new MenteeProfileUpdateRequest(
            "김철수", null, null, null, null, null, null, null
        );

        ApplicationException ex = assertThrows(ApplicationException.class,
            () -> updateService.updateMenteeProfile(2L, request));
        assertThat(ex.getErrorCode()).isEqualTo(MemberErrorCode.MENTEE_PROFILE_NOT_FOUND);
    }
}

