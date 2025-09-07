package com.careerfit.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mento_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentoProfile implements MemberProfile {

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

    @BatchSize(size= 100)
    @ElementCollection
    @CollectionTable(name = "mento_education", joinColumns = @JoinColumn(name = "mento_profile_id"))
    @Column(name = "education")
    @Builder.Default
    private List<String> education = new ArrayList<>();

    @BatchSize(size= 100)
    @ElementCollection
    @CollectionTable(name = "mento_certification", joinColumns = @JoinColumn(name = "mento_profile_id"))
    @Column(name = "certificate")
    @Builder.Default
    private List<String> certifications = new ArrayList<>();

    @BatchSize(size= 100)
    @ElementCollection
    @CollectionTable(name = "mento_expertise", joinColumns = @JoinColumn(name = "mento_profile_id"))
    @Column(name = "expertise")
    @Builder.Default
    private List<String> expertise = new ArrayList<>();


    private String introduction;

    private Double rating;

    private int reviewCount;

    private int menteeCount;

    private Double pricePerSession;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @BatchSize(size= 100)
    @OneToMany(mappedBy = "mentoProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MentoCareer> mentoCareers = new ArrayList<>();

    public static MentoProfile of(int careerYears, String company, String jobPosition, String employmentCertificate,
                                  List<String> certifications, List<String> education, List<String> expertise,
                                  String introduction, List<MentoCareer> mentoCareers) {

        MentoProfile profile = MentoProfile.builder()
                .careerYears(careerYears)
                .company(company)
                .jobPosition(jobPosition)
                .employmentCertificate(employmentCertificate)
                .certifications(certifications != null ? certifications : new ArrayList<>())
                .education(education != null ? education : new ArrayList<>())
                .expertise(expertise)
                .introduction(introduction)
                .rating(0.0)
                .reviewCount(0)
                .menteeCount(0)
                .pricePerSession(0.0)
                .mentoCareers(mentoCareers != null ? mentoCareers : new ArrayList<>())
                .build();

        if (mentoCareers != null) {
            for (MentoCareer career : mentoCareers) {
                career.setMentoProfile(profile);
            }
        }

        return profile;
    }

    public void updateReviewStats(int reviewCount, Double rating) {
        this.reviewCount = reviewCount;
        this.rating = rating;
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }
}
