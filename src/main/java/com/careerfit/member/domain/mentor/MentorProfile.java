package com.careerfit.member.domain.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberProfile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "mentor_profile", indexes = {
        @Index(name = "idx_avg_rating", columnList = "average_rating DESC"),
        @Index(name = "idx_career_years", columnList = "career_years DESC"),
        @Index(name = "idx_review_count", columnList = "review_count DESC"),
        @Index(name = "idx_mentee_count", columnList = "mentee_count DESC"),
        @Index(name = "idx_company", columnList = "company"),
        @Index(name = "idx_job_position", columnList = "job_position")
})
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
    private Integer reviewCount;
    private Integer menteeCount;
    private Integer pricePerSession;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "mentorProfile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @BatchSize(size=200)
    @Builder.Default
    private List<MentorCertification> certifications = new ArrayList<>();

    @OneToMany(mappedBy = "mentorProfile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @BatchSize(size=200)
    @Builder.Default
    private List<MentorEducation> educations = new ArrayList<>();

    @OneToMany(mappedBy = "mentorProfile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @BatchSize(size=200)
    @Builder.Default
    private List<MentorExpertise> expertises = new ArrayList<>();

    @OneToMany(mappedBy = "mentorProfile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @BatchSize(size=200)
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
            .pricePerSession(0)
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

        boolean needsSearchTextUpdate=false;

        if (careerYears != null) {
            this.careerYears = careerYears;
        }
        if (company != null) {
            this.company = company;
            needsSearchTextUpdate=true;
        }
        if (jobPosition != null) {
            this.jobPosition = jobPosition;
            needsSearchTextUpdate=true;
        }
        if (employmentCertificate != null) {
            this.employmentCertificate = employmentCertificate;
        }

        if (introduction != null) {
            this.introduction = introduction;
        }

        if (certifications != null) {
            updateCertifications(certifications);
        }
        if (educations != null) {
            updateEducations(educations);
        }
        if (expertises != null) {
            updateExpertises(expertises);
        }
        if (needsSearchTextUpdate && this.member != null) {
            this.member.updateSearchText(this.company, this.jobPosition);
        }
    }

    private void updateCertifications(List<MentorCertification> newCertifications) {
        Set<String> existing = this.certifications.stream()
                .map(MentorCertification::getCertificateName)
                .collect(Collectors.toSet());
        Set<String> incoming = newCertifications.stream()
                .map(MentorCertification::getCertificateName)
                .collect(Collectors.toSet());

        this.certifications.removeIf(c -> !incoming.contains(c.getCertificateName()));
        newCertifications.stream()
                .filter(c -> !existing.contains(c.getCertificateName()))
                .forEach(this::addCertification);
    }

    private void updateEducations(List<MentorEducation> newEducations) {
        Set<String> existing = this.educations.stream()
                .map(MentorEducation::getSchoolName)
                .collect(Collectors.toSet());
        Set<String> incoming = newEducations.stream()
                .map(MentorEducation::getSchoolName)
                .collect(Collectors.toSet());

        this.educations.removeIf(e -> !incoming.contains(e.getSchoolName()));
        newEducations.stream()
                .filter(e -> !existing.contains(e.getSchoolName()))
                .forEach(this::addEducation);
    }

    private void updateExpertises(List<MentorExpertise> newExpertises) {
        Set<String> existing = this.expertises.stream()
                .map(MentorExpertise::getExpertiseName)
                .collect(Collectors.toSet());
        Set<String> incoming = newExpertises.stream()
                .map(MentorExpertise::getExpertiseName)
                .collect(Collectors.toSet());

        this.expertises.removeIf(e -> !incoming.contains(e.getExpertiseName()));
        newExpertises.stream()
                .filter(e -> !existing.contains(e.getExpertiseName()))
                .forEach(this::addExpertise);
    }

    public void updateReviewStats(int reviewCount, Double averageRating) {
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
    }

}
