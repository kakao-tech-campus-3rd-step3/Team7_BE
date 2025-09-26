package com.careerfit.member.domain.mentee;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentee_wish_position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class MenteeWishPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String positionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_profile_id")
    private MenteeProfile menteeProfile;

    public static MenteeWishPosition of(String positionName) {
        return MenteeWishPosition.builder()
            .positionName(positionName)
            .build();
    }

    public void setMenteeProfile(MenteeProfile menteeProfile) {
        this.menteeProfile = menteeProfile;
    }
}