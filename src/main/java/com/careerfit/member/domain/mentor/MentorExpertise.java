package com.careerfit.member.domain.mentor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentor_expertise")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentorExpertise {
    @Id
    @GeneratedValue
    private Long id;

    private String expertiseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_profile_id")
    private MentorProfile mentorProfile;

    public static MentorExpertise of(String expertiseName) {
        return MentorExpertise.builder()
            .expertiseName(expertiseName)
            .build();
    }

    public void setMentorProfile(MentorProfile mentorProfile) {
        this.mentorProfile = mentorProfile;
    }
}
