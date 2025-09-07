package com.careerfit.member.domain;

import com.careerfit.application.domain.Application;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.global.entity.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String name;

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

    public static Member mento(String email, String phoneNumber, String name, String profileImageUrl, OAuthProvider oAuthProvider,
                               String oauthId, MentoProfile mentoProfile) {
        Member member = Member.builder()
                .name(name)
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

    public static Member mentee(String email, String phoneNumber, String name, String profileImageUrl, OAuthProvider oAuthProvider,
                                String oauthId, MenteeProfile menteeProfile) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .profileImageUrl(profileImageUrl)
                .provider(oAuthProvider)
                .oauthId(oauthId)
                .memberRole(MemberRole.MENTEE)
                .build();
        member.setMemberProfile(menteeProfile);
        return member;
    }

    public void setMemberProfile(MemberProfile memberProfile) {

        if (memberProfile == null) {
            return;
        }
        if (memberProfile instanceof MentoProfile) {
            this.mentoProfile = (MentoProfile) memberProfile;
            this.mentoProfile.setMember(this);
        } else if (memberProfile instanceof MenteeProfile) {
            this.menteeProfile = (MenteeProfile) memberProfile;
            this.menteeProfile.setMember(this);
        }
    }

    public void updateProfileImage(String profileImageUrl) {
        if (profileImageUrl != null) this.profileImageUrl = profileImageUrl;
    }
}
