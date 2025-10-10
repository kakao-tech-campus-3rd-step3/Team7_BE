package com.careerfit.member.domain.mentor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentor_education")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentorEducation {
    @Id
    @GeneratedValue
    private Long id;

    private String schoolName;
    private String major;
    private int startYear;
    private int endYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_profile_id")
    private MentorProfile mentorProfile;

    public static MentorEducation of(String schoolName, String major, int startYear, int endYear) {
        return MentorEducation.builder()
            .schoolName(schoolName)
            .major(major)
            .startYear(startYear)
            .endYear(endYear)
            .build();
    }

    public void setMentorProfile(MentorProfile mentorProfile) {
        this.mentorProfile = mentorProfile;
    }
}
