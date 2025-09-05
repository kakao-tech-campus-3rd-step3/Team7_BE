package com.careerfit.auth.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.auth.dto.SignUpResponse;
import com.careerfit.auth.dto.TokenInfo;
import com.careerfit.auth.exception.AuthErrorCode;
import com.careerfit.auth.utils.JwtUtils;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MenteeProfile;
import com.careerfit.member.domain.MentoProfile;
import com.careerfit.auth.dto.CommonSignUpRequest;
import com.careerfit.auth.dto.MenteeSignUpRequest;
import com.careerfit.auth.dto.MentoSignUpRequest;
import com.careerfit.member.repository.MemberJpaRepository;
import com.careerfit.member.service.MemberFinder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberFinder memberFinder;
    private final JwtUtils jwtUtils;

    @Transactional
    public SignUpResponse signUpAsMentee(MenteeSignUpRequest dto) {
        CommonSignUpRequest commonInfo = dto.commonInfo();
        validateDuplicateEmail(commonInfo.email());

        OAuthProvider oAuthProvider = OAuthProvider.from(commonInfo.registrationId());
        MenteeProfile menteeProfile = MenteeProfile.of(
            dto.university(),
            dto.major(),
            dto.graduationYear(),
            dto.wishCompanies(),
            dto.wishPositions());

        Member mentee = Member.mentee(
            commonInfo.email(),
            commonInfo.phoneNumber(),
            commonInfo.profileImage(),
            oAuthProvider,
            commonInfo.oauthId(),
            menteeProfile);

        memberJpaRepository.save(mentee);

        //TODO: redis에 refreshToken 저장

        TokenInfo tokenInfo = jwtUtils.generateTokens(mentee.getId(),
            Set.of(mentee.getMemberRole().getRole()));
        return SignUpResponse.of(mentee.getId(), tokenInfo);
    }

    @Transactional
    public SignUpResponse signUpAsMento(MentoSignUpRequest dto){
        CommonSignUpRequest commonInfo = dto.commonInfo();
        validateDuplicateEmail(commonInfo.email());

        OAuthProvider oAuthProvider = OAuthProvider.from(commonInfo.registrationId());
        MentoProfile mentoProfile = MentoProfile.of(
            dto.career(),
            dto.currentCompany(),
            dto.currentPosition(),
            dto.certificate(),
            dto.education(),
            dto.expertises(),
            dto.description());

        Member mento = Member.mento(
            commonInfo.email(),
            commonInfo.phoneNumber(),
            commonInfo.profileImage(),
            oAuthProvider,
            commonInfo.oauthId(),
            mentoProfile);

        memberJpaRepository.save(mento);

        //TODO: redis에 refreshToken 저장

        TokenInfo tokenInfo = jwtUtils.generateTokens(mento.getId(),
            Set.of(mento.getMemberRole().getRole()));
        return SignUpResponse.of(mento.getId(), tokenInfo);
    }

    private void validateDuplicateEmail(String email){
        if(memberFinder.getMemberWithOptional(email).isPresent()) {
            throw new ApplicationException(AuthErrorCode.DUPLICATE_EMAIL)
                .addErrorInfo("email", email);
        }
    }

    public String getAuthorizationUrl(String registrationId) {
        return UriComponentsBuilder
            .fromPath("/oauth2/authorization/{registrationId}")
            .buildAndExpand(registrationId)
            .toUriString();
    }
}
