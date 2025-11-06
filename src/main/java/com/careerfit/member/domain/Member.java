package com.careerfit.member.domain;

import com.careerfit.application.domain.Application;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.global.entity.TimeBaseEntity;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentor.MentorProfile;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member", indexes = {
        @Index(name = "idx_member_role", columnList = "member_role"),
        @Index(name = "idx_member_name", columnList = "name")
})
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

    @Column(name = "search_text", length = 500)
    private String searchText;

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
    private MentorProfile mentorProfile;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MenteeProfile menteeProfile;

    public static Member mentor(String email, String phoneNumber, String name,
                                String profileImageUrl, OAuthProvider oAuthProvider,
                                String oauthId, MentorProfile mentorProfile) {
        Member member = Member.builder()
            .name(name)
            .email(email)
            .phoneNumber(phoneNumber)
            .profileImageUrl(profileImageUrl)
            .provider(oAuthProvider)
            .oauthId(oauthId)
            .memberRole(MemberRole.MENTOR)
            .build();

        member.setMemberProfile(mentorProfile);
        if (mentorProfile != null) {
            member.updateSearchText(mentorProfile.getCompany(), mentorProfile.getJobPosition());
        }
        return member;
    }

    public static Member mentee(String email, String phoneNumber, String name,
                                String profileImageUrl, OAuthProvider oAuthProvider,
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
        if (memberProfile instanceof MentorProfile) {
            this.mentorProfile = (MentorProfile) memberProfile;
            this.mentorProfile.setMember(this);
        } else if (memberProfile instanceof MenteeProfile) {
            this.menteeProfile = (MenteeProfile) memberProfile;
            this.menteeProfile.setMember(this);
        }
    }

    public void updateProfileImage(String profileImageUrl) {
        if (profileImageUrl != null) {
            this.profileImageUrl = profileImageUrl;
        }
    }
    public void updateSearchText(String company, String jobPosition) {
        if (!this.isMentor()) {
            return;
        }

        this.searchText = String.join(" ",
                this.name != null ? this.name : "",
                company != null ? company : "",
                jobPosition != null ? jobPosition : ""
        ).trim();
    }

    public boolean isMentor() {
        return this.memberRole == MemberRole.MENTOR;
    }

    public boolean isMentee() {
        return this.memberRole == MemberRole.MENTEE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

}
