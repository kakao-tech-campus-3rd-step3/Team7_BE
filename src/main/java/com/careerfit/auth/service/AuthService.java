package com.careerfit.auth.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.auth.dto.CommonSignUpRequest;
import com.careerfit.auth.dto.MenteeSignUpRequest;
import com.careerfit.auth.dto.MentorSignUpRequest;
import com.careerfit.auth.dto.ReissueTokenRequest;
import com.careerfit.auth.dto.SignUpResponse;
import com.careerfit.auth.dto.TokenInfo;
import com.careerfit.auth.exception.AuthErrorCode;
import com.careerfit.auth.utils.JwtParser;
import com.careerfit.auth.utils.JwtProvider;
import com.careerfit.auth.utils.JwtValidator;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import com.careerfit.member.domain.mentor.MentorCareer;
import com.careerfit.member.domain.mentor.MentorCertification;
import com.careerfit.member.domain.mentor.MentorEducation;
import com.careerfit.member.domain.mentor.MentorExpertise;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.repository.MemberJpaRepository;
import com.careerfit.member.service.MemberFinder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberFinder memberFinder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final JwtParser jwtParser;
    private final JwtValidator jwtValidator;

    @Transactional
    public SignUpResponse signUpAsMentee(MenteeSignUpRequest dto) {
        CommonSignUpRequest commonInfo = dto.commonInfo();
        validateDuplicateEmail(commonInfo.email());

        OAuthProvider oAuthProvider = OAuthProvider.from(commonInfo.registrationId());

        List<MenteeWishCompany> wishCompanies = dto.wishCompanies().stream()
            .map(wishCompany ->
                MenteeWishCompany.of(wishCompany.companyName()))
            .toList();

        List<MenteeWishPosition> wishPositions = dto.wishPositions().stream()
            .map(wishPosition ->
                MenteeWishPosition.of(wishPosition.positionName()))
            .toList();

        MenteeProfile menteeProfile = MenteeProfile.of(
            dto.university(),
            dto.major(),
            dto.graduationYear(),
            wishCompanies,
            wishPositions
        );

        Member mentee = Member.mentee(
            commonInfo.email(),
            commonInfo.phoneNumber(),
            commonInfo.name(),
            commonInfo.profileImage(),
            oAuthProvider,
            commonInfo.oauthId(),
            menteeProfile);
        memberJpaRepository.save(mentee);

        TokenInfo tokenInfo = issueTokens(mentee);

        return SignUpResponse.of(mentee.getId(), tokenInfo);
    }

    @Transactional
    public SignUpResponse signUpAsMentor(MentorSignUpRequest dto) {
        CommonSignUpRequest commonInfo = dto.commonInfo();
        validateDuplicateEmail(commonInfo.email());

        OAuthProvider oAuthProvider = OAuthProvider.from(commonInfo.registrationId());

        List<MentorCareer> careers = dto.careers().stream()
            .map(career ->
                MentorCareer.of(career.companyName(), career.position(), career.startDate(),
                    career.endDate()))
            .toList();

        List<MentorCertification> certifications = dto.certifications().stream()
            .map(c -> MentorCertification.of(c.certificateName()))
            .toList();

        List<MentorEducation> educations = dto.educations().stream()
            .map(e -> MentorEducation.of(e.schoolName(), e.major(), e.startYear(), e.endYear()))
            .toList();

        List<MentorExpertise> expertises = dto.expertises().stream()
            .map(e -> MentorExpertise.of(e.expertiseName()))
            .toList();

        MentorProfile mentorProfile = MentorProfile.of(
            dto.careerYears(),
            dto.currentCompany(),
            dto.currentPosition(),
            dto.employmentCertificate(),
            certifications,
            educations,
            expertises,
            dto.description(),
            careers,
            0.0
        );

        Member mentor = Member.mentor(
            commonInfo.email(),
            commonInfo.phoneNumber(),
            commonInfo.name(),
            commonInfo.profileImage(),
            oAuthProvider,
            commonInfo.oauthId(),
            mentorProfile);
        memberJpaRepository.save(mentor);

        TokenInfo tokenInfo = issueTokens(mentor);

        return SignUpResponse.of(mentor.getId(), tokenInfo);
    }

    public TokenInfo issueTokens(Member member) {
        String accessToken = jwtProvider.createAccessToken(member.getId(),
            Set.of(member.getMemberRole().getRole()));
        String refreshToken = jwtProvider.createRefreshToken(member.getId());
        refreshTokenService.cacheRefreshToken(member.getId(), refreshToken);

        return TokenInfo.of(jwtProvider.getTokenType(), accessToken, refreshToken,
            jwtProvider.getAccessTokenExpiration(), jwtProvider.getRefreshTokenExpiration());
    }

    public TokenInfo reissueTokens(ReissueTokenRequest reissueTokenRequest) {
        String refreshToken = reissueTokenRequest.refreshToken();
        if (!jwtValidator.validateToken(refreshToken) || !refreshTokenService.existsByRefreshToken(
            refreshToken)) {
            throw new ApplicationException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtParser.getUserId(refreshToken);
        Member member = memberFinder.getMemberOrThrow(userId);
        return issueTokens(member);
    }

    public void logout(Long userId) {
        refreshTokenService.deleteByMemberId(userId);
    }

    private void validateDuplicateEmail(String email) {
        if (memberFinder.getMemberWithOptional(email).isPresent()) {
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
