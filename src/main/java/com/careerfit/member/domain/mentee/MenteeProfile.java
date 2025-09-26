package com.careerfit.member.domain.mentee;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "menteeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenteeWishCompany> wishCompanies = new ArrayList<>();

    @OneToMany(mappedBy = "menteeProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MenteeWishPosition> wishPositions = new ArrayList<>();

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static MenteeProfile of(String university, String major, Integer graduationYear,
                                   List<MenteeWishCompany> wishCompanies,
                                   List<MenteeWishPosition> wishPositions) {
        MenteeProfile profile = MenteeProfile.builder()
            .university(university)
            .major(major)
            .graduationYear(graduationYear)
            .build();

        if (wishCompanies != null) wishCompanies.forEach(profile::addWishCompany);
        if (wishPositions != null) wishPositions.forEach(profile::addWishPosition);

        return profile;
    }

    public void addWishCompany(MenteeWishCompany wishCompany) {
        wishCompany.setMenteeProfile(this);
        this.wishCompanies.add(wishCompany);
    }

    public void addWishPosition(MenteeWishPosition wishPosition) {
        wishPosition.setMenteeProfile(this);
        this.wishPositions.add(wishPosition);
    }

    @Override
    public void setMember(Member member) {
        this.member = member;
    }

    public void updateProfile(String university, String major, Integer graduationYear,
                              List<MenteeWishCompany> wishCompanies,
                              List<MenteeWishPosition> wishPositions) {
        if (university != null) this.university = university;
        if (major != null) this.major = major;
        if (graduationYear != null) this.graduationYear = graduationYear;

        if (wishCompanies != null) {
            this.wishCompanies.clear();
            wishCompanies.forEach(this::addWishCompany);
        }

        if (wishPositions != null) {
            this.wishPositions.clear();
            wishPositions.forEach(this::addWishPosition);
        }
    }
}
