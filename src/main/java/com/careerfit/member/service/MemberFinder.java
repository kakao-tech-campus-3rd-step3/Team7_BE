package com.careerfit.member.service;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberRole;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFinder {

    private final MemberJpaRepository memberJpaRepository;

    public Member getMemberOrThrow(Long memberId) {
        return memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new ApplicationException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member getMentorOrThrow(Long memberId) {
        return Optional.of(getMemberOrThrow(memberId))
            .filter(Member::isMentor)
            .orElseThrow(() -> new ApplicationException(MemberErrorCode.MENTOR_NOT_FOUND));
    }

    public Member getMenteeOrThrow(Long memberId) {
        return Optional.of(getMemberOrThrow(memberId))
            .filter(Member::isMentee)
            .orElseThrow(() -> new ApplicationException(MemberErrorCode.MENTEE_NOT_FOUND));
    }

    public Optional<Member> getMemberWithOptional(String registrationId, String oauthId) {
        return memberJpaRepository.findByProviderAndOauthId(OAuthProvider.from(registrationId),
            oauthId);
    }

    public Optional<Member> getMemberWithOptional(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    public Page<Member> getMentorPage(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return memberJpaRepository.findByMemberRoleOrderByMentorProfile_AverageRatingDesc(MemberRole.MENTOR, pageable);
        }

        return memberJpaRepository.findDistinctByMemberRoleAndNameContainsIgnoreCaseOrMentorProfile_CompanyContainsIgnoreCaseOrMentorProfile_JobPositionContainsIgnoreCase(
            MemberRole.MENTOR,
            search, search, search,
            pageable
        );
    }

}
