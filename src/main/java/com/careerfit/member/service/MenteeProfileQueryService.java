package com.careerfit.member.service;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MenteeProfile;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenteeProfileQueryService {

    private final MemberJpaRepository memberJpaRepository;

    public MenteeProfileInfo getMenteeProfile(Long memberId) {
        Member member = memberJpaRepository.findMenteeByIdWithDetails(memberId)
                .orElseThrow(() -> new ApplicationException(MemberErrorCode.MEMBER_NOT_FOUND));

        MenteeProfile profile = member.getMenteeProfile();

        return new MenteeProfileInfo(
                member.getName(),
                member.getEmail(),
                member.getPhoneNumber(),
                profile.getUniversity(),
                profile.getMajor(),
                profile.getGraduationYear(),
                new ArrayList<>(profile.getWishCompany()),
                new ArrayList<>(profile.getWishPosition())
        );
    }
}
