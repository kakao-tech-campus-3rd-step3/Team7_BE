package com.careerfit.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mento_career")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentoCareer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String position;

    private String startDate;
    private String endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mento_profile_id")
    private MentoProfile mentoProfile;

    public void setMentoProfile(MentoProfile mentoProfile) {
        this.mentoProfile = mentoProfile;
    }
}
