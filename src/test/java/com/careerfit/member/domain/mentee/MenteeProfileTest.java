package com.careerfit.member.domain.mentee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MenteeProfileTest {

    private MenteeProfile profile;

    @BeforeEach
    void setUp() {
        profile = MenteeProfile.of("서울대", "컴퓨터공학", 2025, null, null);
    }

    @Test
    void updateProfile_updatesFieldsAndCollections() {
        profile.updateProfile(
            "연세대",
            "소프트웨어학과",
            2026,
            List.of(MenteeWishCompany.builder().companyName("삼성").build()),
            List.of(MenteeWishPosition.builder().positionName("백엔드").build())
        );

        assertThat(profile.getUniversity()).isEqualTo("연세대");
        assertThat(profile.getMajor()).isEqualTo("소프트웨어학과");
        assertThat(profile.getGraduationYear()).isEqualTo(2026);

        assertThat(profile.getWishCompanies()).hasSize(1);
        assertThat(profile.getWishCompanies().get(0).getCompanyName()).isEqualTo("삼성");

        assertThat(profile.getWishPositions()).hasSize(1);
        assertThat(profile.getWishPositions().get(0).getPositionName()).isEqualTo("백엔드");
    }

    @Test
    void updateProfile_nullValues_doNotOverwrite() {
        profile.updateProfile(null, null, null, null, null);

        assertThat(profile.getUniversity()).isEqualTo("서울대");
        assertThat(profile.getMajor()).isEqualTo("컴퓨터공학");
        assertThat(profile.getGraduationYear()).isEqualTo(2025);
        assertThat(profile.getWishCompanies()).isEmpty();
        assertThat(profile.getWishPositions()).isEmpty();
    }
}
