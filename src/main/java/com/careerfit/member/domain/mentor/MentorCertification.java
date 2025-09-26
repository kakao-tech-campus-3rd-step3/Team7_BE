package com.careerfit.member.domain.mentor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentor_certification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentorCertification {
    @Id
    @GeneratedValue
    private Long id;

    private String certificateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_profile_id")
    private MentorProfile mentorProfile;

    public static MentorCertification of(String certificationName) {
        return MentorCertification.builder()
            .certificateName(certificationName)
            .build();
    }

    public void setMentorProfile(MentorProfile mentorProfile) {
        this.mentorProfile = mentorProfile;
    }
}