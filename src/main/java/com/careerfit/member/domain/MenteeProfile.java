package com.careerfit.member.domain;

import jakarta.persistence.Column;
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

    private String wishCompany;

    private String wishPosition;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static MenteeProfile of(String university, String major, Integer graduationYear, String wishCompany, String wishPosition) {
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
}
