package com.careerfit.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mentee_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MenteeProfile implements MemberProfile {

    @Id
    private Long id;

    @Column(nullable = false)
    private String university;

    @Column(nullable = false)
    private String major;

    private Integer graduationYear;

    @BatchSize(size = 100)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "wish_company",
        joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "company_name")
    @Builder.Default
    private List<String> wishCompany = new ArrayList<>();

    @BatchSize(size = 100)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "wish_position",
        joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "position_name")
    @Builder.Default
    private List<String> wishPosition = new ArrayList<>();

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static MenteeProfile of(String university, String major, Integer graduationYear,
        List<String> wishCompany, List<String> wishPosition) {
        return MenteeProfile.builder()
            .university(university)
            .major(major)
            .graduationYear(graduationYear)
            .wishCompany(wishCompany)
            .wishPosition(wishPosition)
            .build();
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }

    public void updateProfile(String university, String major, Integer graduationYear,
        List<String> wishCompany, List<String> wishPosition) {
        if (university != null) {
            this.university = university;
        }
        if (major != null) {
            this.major = major;
        }
        if (graduationYear != null) {
            this.graduationYear = graduationYear;
        }
        if (wishCompany != null) {
            this.wishCompany = wishCompany;
        }
        if (wishPosition != null) {
            this.wishPosition = wishPosition;
        }
    }
}
