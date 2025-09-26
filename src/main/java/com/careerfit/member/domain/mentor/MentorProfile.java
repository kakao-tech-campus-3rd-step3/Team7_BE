package com.careerfit.member.domain.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberProfile;
import jakarta.persistence.*;
import lombok.*;

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

    private String introduction;
    private Double averageRating;
    private int reviewCount;
    private int menteeCount;
    private Double pricePerSession;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "mentorProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MentorCertification> certifications = new ArrayList<>();

    @OneToMany(mappedBy = "mentorProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MentorEducation> educations = new ArrayList<>();

    @OneToMany(mappedBy = "mentorProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MentorExpertise> expertises = new ArrayList<>();

    @OneToMany(mappedBy = "mentorProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MentorCareer> mentorCareers = new ArrayList<>();

    public static MentorProfile of(int careerYears, String company, String jobPosition,
                                   String employmentCertificate,
                                   List<MentorCertification> certifications,
                                   List<MentorEducation> educations,
                                   List<MentorExpertise> expertises,
                                   String introduction,
                                   List<MentorCareer> mentorCareers,
                                   Double averageRating) {

        MentorProfile profile = MentorProfile.builder()
            .careerYears(careerYears)
            .company(company)
            .jobPosition(jobPosition)
            .employmentCertificate(employmentCertificate)
            .certifications(new ArrayList<>())
            .educations(new ArrayList<>())
            .expertises(new ArrayList<>())
            .introduction(introduction)
            .averageRating(averageRating)
            .reviewCount(0)
            .menteeCount(0)
            .pricePerSession(0.0)
            .build();

        if (certifications != null) certifications.forEach(profile::addCertification);
        if (educations != null) educations.forEach(profile::addEducation);
        if (expertises != null) expertises.forEach(profile::addExpertise);
        if (mentorCareers != null) mentorCareers.forEach(profile::addMentoCareer);

        return profile;
    }

    public void addCertification(MentorCertification certification) {
        certification.setMentorProfile(this);
        this.certifications.add(certification);
    }

    public void addEducation(MentorEducation education) {
        education.setMentorProfile(this);
        this.educations.add(education);
    }

    public void addExpertise(MentorExpertise expertise) {
        expertise.setMentorProfile(this);
        this.expertises.add(expertise);
    }

    public void addMentoCareer(MentorCareer mentorCareer) {
        mentorCareer.setMentorProfile(this);
        this.mentorCareers.add(mentorCareer);
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }

    public void updateProfile(Integer careerYears, String company, String jobPosition,
                              String employmentCertificate,
                              List<MentorCertification> certifications,
                              List<MentorEducation> educations,
                              List<MentorExpertise> expertises,
                              String introduction) {

        if (careerYears != null) this.careerYears = careerYears;
        if (company != null) this.company = company;
        if (jobPosition != null) this.jobPosition = jobPosition;
        if (employmentCertificate != null) this.employmentCertificate = employmentCertificate;
        if (introduction != null) this.introduction = introduction;

        if (certifications != null) {
            this.certifications.clear();
            certifications.forEach(this::addCertification);
        }
        if (educations != null) {
            this.educations.clear();
            educations.forEach(this::addEducation);
        }
        if (expertises != null) {
            this.expertises.clear();
            expertises.forEach(this::addExpertise);
        }
    }

    public void updateReviewStats(int reviewCount, Double averageRating) {
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
    }

}
