package com.careerfit.member.service;

import org.springframework.stereotype.Service;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MenteeProfile;
import com.careerfit.member.domain.MentoProfile;
import com.careerfit.member.dto.CommonSignUpRequest;
import com.careerfit.member.dto.MenteeSignUpRequest;
import com.careerfit.member.dto.MentoSignUpRequest;
import com.careerfit.member.repository.MemberJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public void signUpAsMentee(MenteeSignUpRequest dto) {
        CommonSignUpRequest commonInfo = dto.commonInfo();
        OAuthProvider oAuthProvider = OAuthProvider.from(commonInfo.oauthProvider());
        MenteeProfile menteeProfile = MenteeProfile.of(dto.university(), dto.major(),
            dto.graduationYear(), dto.wishCompanies(), dto.wishPositions());

        Member mentee = Member.mentee(commonInfo.email(), commonInfo.phoneNumber(),
            commonInfo.profileImage(), oAuthProvider, commonInfo.oauthId(), menteeProfile);

        memberJpaRepository.save(mentee);
    }

    public void signUpAsMento(MentoSignUpRequest dto){
        CommonSignUpRequest commonInfo = dto.commonInfo();
        OAuthProvider oAuthProvider = OAuthProvider.from(commonInfo.oauthProvider());
        MentoProfile mentoProfile = MentoProfile.of(dto.career(), dto.currentCompany(),
            dto.currentPosition(), dto.certificate(), dto.education(), dto.expertises(),
            dto.description());

        Member mento = Member.mento(commonInfo.email(), commonInfo.phoneNumber(),
            commonInfo.profileImage(), oAuthProvider,
            commonInfo.oauthId(), mentoProfile);

        memberJpaRepository.save(mento);
    }
}
