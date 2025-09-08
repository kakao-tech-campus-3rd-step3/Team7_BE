package com.careerfit.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mentor_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MentorProfile implements MemberProfile {

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
    @CollectionTable(name = "mentor_education", joinColumns = @JoinColumn(name = "mentor_profile_id"))
    @Column(name = "education")
    @Builder.Default
    private List<String> educations = new ArrayList<>();

    @BatchSize(size= 100)
    @ElementCollection
    @CollectionTable(name = "mentor_certification", joinColumns = @JoinColumn(name = "mentor_profile_id"))
    @Column(name = "certificate")
    @Builder.Default
    private List<String> certifications = new ArrayList<>();

    @BatchSize(size= 100)
    @ElementCollection
    @CollectionTable(name = "mentor_expertise", joinColumns = @JoinColumn(name = "mentor_profile_id"))
    @Column(name = "expertise")
    @Builder.Default
    private List<String> expertises = new ArrayList<>();

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
    @OneToMany(mappedBy = "mentorProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MentorCareer> mentoCareers = new ArrayList<>();

    public static MentorProfile of(int careerYears, String company, String jobPosition, String employmentCertificate,
                                  List<String> certifications, List<String> educations, List<String> expertises,
                                  String introduction, List<MentorCareer> mentoCareers) {

        MentorProfile profile = MentorProfile.builder()
                .careerYears(careerYears)
                .company(company)
                .jobPosition(jobPosition)
                .employmentCertificate(employmentCertificate)
                .certifications(certifications != null ? certifications : new ArrayList<>())
                .educations(educations != null ? educations : new ArrayList<>())
                .expertises(expertises != null ? expertises : new ArrayList<>())
                .introduction(introduction)
                .rating(0.0)
                .reviewCount(0)
                .menteeCount(0)
                .pricePerSession(0.0)
                .build();

        if (mentoCareers != null) {
            mentoCareers.forEach(profile::addMentoCareer);
        }

        return profile;
    }

    public void addMentoCareer(MentorCareer mentorCareer) {
        mentorCareer.setMentorProfile(this);
        this.mentoCareers.add(mentorCareer);
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }
}
