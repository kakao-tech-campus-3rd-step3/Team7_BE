package com.careerfit.auth.service;

import java.util.List;
import java.util.Set;

import com.careerfit.member.domain.MentorProfile;
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
import com.careerfit.member.domain.MenteeProfile;
import com.careerfit.member.domain.MentorCareer;
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

        MenteeProfile menteeProfile = MenteeProfile.of(
            dto.university(),
            dto.major(),
            dto.graduationYear(),
            dto.wishCompanies(),
            dto.wishPositions());

        Member mentee = Member.mentee(
            commonInfo.name(),
            commonInfo.email(),
            commonInfo.phoneNumber(),
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

        MentorProfile mentorProfile = MentorProfile.of(
            dto.careerYears(),
            dto.currentCompany(),
            dto.currentPosition(),
            dto.employmentCertificate(),
            dto.certifications(),
            dto.educations(),
            dto.expertises(),
            dto.description(),
            careers,
            0.0 // 회원가입이기 때문에 아직 리뷰 기록이 없으므로 0.0임.
        );

        Member mentor = Member.mentor(
            commonInfo.name(),
            commonInfo.email(),
            commonInfo.phoneNumber(),
            commonInfo.profileImage(),
            oAuthProvider,
            commonInfo.oauthId(),
            mentorProfile);
        memberJpaRepository.save(mentor);

        TokenInfo tokenInfo = issueTokens(mentor);

        return SignUpResponse.of(mentor.getId(), tokenInfo);
    }

    public TokenInfo issueTokens(Member mentor) {
        String accessToken = jwtProvider.createAccessToken(mentor.getId(),
            Set.of(mentor.getMemberRole().getRole()));
        String refreshToken = jwtProvider.createRefreshToken(mentor.getId());
        refreshTokenService.cacheRefreshToken(mentor.getId(), refreshToken);

        return TokenInfo.of(jwtProvider.getTokenType(), accessToken, refreshToken,
            jwtProvider.getAccessTokenExpiration(), jwtProvider.getRefreshTokenExpiration());
    }

    public TokenInfo reissueTokens(ReissueTokenRequest reissueTokenRequest) {
        String refreshToken = reissueTokenRequest.refreshToken();
        if(!jwtValidator.validateToken(refreshToken) || !refreshTokenService.existsByRefreshToken(refreshToken)){
            throw new ApplicationException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtParser.getUserId(refreshToken);
        Member member = memberFinder.getMemberOrThrow(userId);
        return issueTokens(member);
    }

    public void logout(Long userId){
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
