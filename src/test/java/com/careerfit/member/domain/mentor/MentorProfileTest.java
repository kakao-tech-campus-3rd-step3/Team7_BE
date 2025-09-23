package com.careerfit.member.domain.mentor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MentorProfileTest {

    private MentorProfile profile;

    @BeforeEach
    void setUp() {
        profile = MentorProfile.of(
            10,
            "삼성전자",
            "Backend Developer",
            "재직증명서",
            null, null, null,
            "안녕하세요",
            null,
            4.5
        );
    }

    @Test
    void updateProfile_updatesAllFieldsAndCollections() {
        profile.updateProfile(
            12,
            "네이버",
            "Fullstack Developer",
            "재직증명서2",
            List.of(MentorCertification.builder().certificateName("AWS").build()),
            List.of(MentorEducation.builder().schoolName("서울대").major("컴공").startYear(2010).endYear(2014).build()),
            List.of(MentorExpertise.builder().expertiseName("Spring").build()),
            "안녕하세요2"
        );

        assertThat(profile.getCareerYears()).isEqualTo(12);
        assertThat(profile.getCompany()).isEqualTo("네이버");
        assertThat(profile.getJobPosition()).isEqualTo("Fullstack Developer");
        assertThat(profile.getEmploymentCertificate()).isEqualTo("재직증명서2");
        assertThat(profile.getCertifications()).hasSize(1);
        assertThat(profile.getCertifications().get(0).getCertificateName()).isEqualTo("AWS");
        assertThat(profile.getEducations()).hasSize(1);
        assertThat(profile.getEducations().get(0).getSchoolName()).isEqualTo("서울대");
        assertThat(profile.getExpertises()).hasSize(1);
        assertThat(profile.getExpertises().get(0).getExpertiseName()).isEqualTo("Spring");
        assertThat(profile.getIntroduction()).isEqualTo("안녕하세요2");
    }

    @Test
    void updateProfile_nullValues_doNotOverwrite() {
        profile.updateProfile(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        );

        assertThat(profile.getCareerYears()).isEqualTo(10);
        assertThat(profile.getCompany()).isEqualTo("삼성전자");
        assertThat(profile.getJobPosition()).isEqualTo("Backend Developer");
        assertThat(profile.getEmploymentCertificate()).isEqualTo("재직증명서");
        assertThat(profile.getCertifications()).isEmpty();
        assertThat(profile.getEducations()).isEmpty();
        assertThat(profile.getExpertises()).isEmpty();
        assertThat(profile.getIntroduction()).isEqualTo("안녕하세요");
    }
}
