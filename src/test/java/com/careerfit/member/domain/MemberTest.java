package com.careerfit.member.domain;

import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentor.MentorProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    private Member mentor;
    private Member mentee;
    private MentorProfile mentorProfile;
    private MenteeProfile menteeProfile;

    @BeforeEach
    void setUp() {
        mentorProfile = MentorProfile.of(5, "삼성전자", "Backend", "재직증명서", null, null, null, "소개", null, 4.5);
        menteeProfile = MenteeProfile.of("서울대", "컴공", 2025, null, null);

        mentor = Member.mentor("mentor@test.com", "010-1111-1111", "멘토", null, null, "oauth123", mentorProfile);
        mentee = Member.mentee("mentee@test.com", "010-2222-2222", "멘티", null, null, "oauth456", menteeProfile);
    }

    @Test
    void setMemberProfile_assignsProfileCorrectly() {
        assertThat(mentor.getMentorProfile()).isEqualTo(mentorProfile);
        assertThat(mentorProfile.getMember()).isEqualTo(mentor);

        assertThat(mentee.getMenteeProfile()).isEqualTo(menteeProfile);
        assertThat(menteeProfile.getMember()).isEqualTo(mentee);
    }

    @Test
    void isMentorAndIsMentee_checkRoles() {
        assertThat(mentor.isMentor()).isTrue();
        assertThat(mentor.isMentee()).isFalse();

        assertThat(mentee.isMentee()).isTrue();
        assertThat(mentee.isMentor()).isFalse();
    }

    @Test
    void updateProfileImage_changesOnlyIfNotNull() {
        mentor.updateProfileImage("newUrl");
        assertThat(mentor.getProfileImageUrl()).isEqualTo("newUrl");

        mentor.updateProfileImage(null);
        assertThat(mentor.getProfileImageUrl()).isEqualTo("newUrl");
    }
}

