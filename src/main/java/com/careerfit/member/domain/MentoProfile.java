package com.careerfit.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mento_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentoProfile {

    @Id
    private Long id;

    @Column(nullable = false)
    private int careerYears;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String jobPosition;

    @Column(nullable = false)
    private String employmentCertificate;

    private String education;

    @Lob
    private String expertise;

    private String introduction;

    private Double rating;

    private int reviewCount;

    private int menteeCount;

    private Double avgResponseTime;

    private Double pricePerSession;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static MentoProfile of(int careerYears, String company, String jobPosition, String employmentCertificate, String education,
        String expertise, String introduction){

        return MentoProfile.builder()
            .careerYears(careerYears)
            .company(company)
            .jobPosition(jobPosition)
            .employmentCertificate(employmentCertificate)
            .education(education)
            .expertise(expertise)
            .introduction(introduction)
            .rating(null)
            .reviewCount(0)
            .menteeCount(0)
            .avgResponseTime(null)
            .pricePerSession(null)
            .build();
    }

}
