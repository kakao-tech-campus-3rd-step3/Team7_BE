package com.careerfit.member.domain.mentee;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mentee_wish_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class MenteeWishCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_profile_id")
    private MenteeProfile menteeProfile;

    public static MenteeWishCompany of(String companyName) {
        return MenteeWishCompany.builder()
            .companyName(companyName)
            .build();
    }

    public void setMenteeProfile(MenteeProfile menteeProfile) {
        this.menteeProfile = menteeProfile;
    }
}
