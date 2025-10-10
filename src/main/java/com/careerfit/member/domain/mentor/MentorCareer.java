package com.careerfit.member.domain.mentor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentor_career")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentorCareer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String position;
    private String startDate;
    private String endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_profile_id")
    private MentorProfile mentorProfile;

    public static MentorCareer of(String companyName, String position, String startDate, String endDate) {
        return MentorCareer.builder()
            .companyName(companyName)
            .position(position)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

    public void setMentorProfile(MentorProfile mentorProfile) {
        this.mentorProfile = mentorProfile;
    }
}
