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

    @OneToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    private MentoProfile mentoProfile;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MenteeProfile menteeProfile;

    public static Member mento(String email, String phoneNumber, String profileImageUrl, OAuthProvider oAuthProvider,
        String oauthId, MentoProfile mentoProfile) {
        return Member.builder()
            .email(email)
            .phoneNumber(phoneNumber)
            .profileImageUrl(profileImageUrl)
            .provider(oAuthProvider)
            .oauthId(oauthId)
            .memberRole(MemberRole.MENTO)
            .mentoProfile(mentoProfile)
            .build();
    }

    public static Member mentee(String email, String phoneNumber, String profileImageUrl, OAuthProvider oAuthProvider,
        String oauthId, MenteeProfile menteeProfile) {
        return Member.builder()
            .email(email)
            .phoneNumber(phoneNumber)
            .profileImageUrl(profileImageUrl)
            .provider(oAuthProvider)
            .oauthId(oauthId)
            .memberRole(MemberRole.MENTEE)
            .menteeProfile(menteeProfile)
            .build();
    }

    public setMemeberProfile()

}
