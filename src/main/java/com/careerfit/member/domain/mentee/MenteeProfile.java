package com.careerfit.member.domain.mentee;

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

    @OneToMany(mappedBy = "menteeProfile", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true)
    @BatchSize(size=200)
    @Builder.Default
    private List<MenteeWishCompany> wishCompanies = new ArrayList<>();

    @OneToMany(mappedBy = "menteeProfile", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @BatchSize(size=200)
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
            updateWishCompanies(wishCompanies);
        }

        if (wishPositions != null) {
            updateWishPositions(wishPositions);
        }
    }

    private void updateWishCompanies(List<MenteeWishCompany> newCompanies) {

        Set<String> existing = this.wishCompanies.stream()
                .map(MenteeWishCompany::getCompanyName)
                .collect(Collectors.toSet());

        Set<String> incoming = newCompanies.stream()
                .map(MenteeWishCompany::getCompanyName)
                .collect(Collectors.toSet());

        this.wishCompanies.removeIf(company -> !incoming.contains(company.getCompanyName()));

        newCompanies.stream()
                .filter(newCompany -> !existing.contains(newCompany.getCompanyName()))
                .forEach(this::addWishCompany);
    }

    private void updateWishPositions(List<MenteeWishPosition> newPositions) {
        Set<String> existing = this.wishPositions.stream()
                .map(MenteeWishPosition::getPositionName)
                .collect(Collectors.toSet());

        Set<String> incoming = newPositions.stream()
                .map(MenteeWishPosition::getPositionName)
                .collect(Collectors.toSet());

        this.wishPositions.removeIf(position -> !incoming.contains(position.getPositionName()));

        newPositions.stream()
                .filter(newPosition -> !existing.contains(newPosition.getPositionName()))
                .forEach(this::addWishPosition);
    }
}
