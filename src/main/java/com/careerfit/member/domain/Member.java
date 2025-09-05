package com.careerfit.member.domain;

import java.util.ArrayList;
import java.util.List;

import com.careerfit.application.domain.Application;
import com.careerfit.global.entity.TimeBaseEntity;
import com.careerfit.auth.domain.OAuthProvider;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private String profileImageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @Column(nullable = false)
    private String oauthId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Application> applications = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MentoProfile mentoProfile;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MenteeProfile menteeProfile;

    public static Member mento(String email, String phoneNumber, String profileImageUrl, OAuthProvider oAuthProvider,
        String oauthId, MentoProfile mentoProfile) {
        Member member = Member.builder()
            .email(email)
            .phoneNumber(phoneNumber)
            .profileImageUrl(profileImageUrl)
            .provider(oAuthProvider)
            .oauthId(oauthId)
            .memberRole(MemberRole.MENTO)
            .build();

        member.setMemberProfile(mentoProfile);

        return member;
    }

    public static Member mentee(String email, String phoneNumber, String profileImageUrl, OAuthProvider oAuthProvider,
        String oauthId, MenteeProfile menteeProfile) {
        Member member = Member.builder()
            .email(email)
            .phoneNumber(phoneNumber)
            .profileImageUrl(profileImageUrl)
            .provider(oAuthProvider)
            .oauthId(oauthId)
            .memberRole(MemberRole.MENTEE)
            .menteeProfile(menteeProfile)
            .build();

        member.setMemberProfile(menteeProfile);
        return member;
    }

    private void setMemberProfile(MemberProfile memberProfile) {
        if (memberProfile instanceof MentoProfile) {
            this.mentoProfile = (MentoProfile) memberProfile;
        } else if (memberProfile instanceof MenteeProfile) {
            this.menteeProfile = (MenteeProfile) memberProfile;
        }
        memberProfile.setMember(this);
    }

}
