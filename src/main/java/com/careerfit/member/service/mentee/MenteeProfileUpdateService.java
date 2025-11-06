package com.careerfit.member.service.mentee;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.domain.mentee.MenteeWishCompany;
import com.careerfit.member.domain.mentee.MenteeWishPosition;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.dto.mentee.MenteeProfileUpdateRequest;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenteeProfileUpdateService {

    private final MemberFinder menteeFinder;

    public MenteeProfileInfo updateMenteeProfile(Long menteeId, MenteeProfileUpdateRequest request) {

        Member mentee = menteeFinder.getMenteeOrThrow(menteeId);

        MenteeProfile profile = mentee.getMenteeProfile();
        if (profile == null) {
            throw new ApplicationException(MemberErrorCode.MENTEE_PROFILE_NOT_FOUND);
        }

        if (request.name() != null) mentee.setName(request.name());
        if (request.email() != null) mentee.setEmail(request.email());
        if (request.phoneNumber() != null) mentee.setPhoneNumber(request.phoneNumber());
        if (request.profileImageUrl() != null) mentee.setProfileImageUrl(request.profileImageUrl());
        List<MenteeWishCompany> wishCompanies = null;
        if (request.wishCompanies() != null) {
            wishCompanies = request.wishCompanies().stream()
                .map(wc -> MenteeWishCompany.builder()
                    .companyName(wc.companyName())
                    .menteeProfile(profile)
                    .build())
                .toList();
        }

        List<MenteeWishPosition> wishPositions = null;
        if (request.wishPositions() != null) {
            wishPositions = request.wishPositions().stream()
                .map(wp -> MenteeWishPosition.builder()
                    .positionName(wp.positionName())
                    .menteeProfile(profile)
                    .build())
                .toList();
        }

        profile.updateProfile(
            request.university(),
            request.major(),
            request.graduationYear(),
            wishCompanies,
            wishPositions
        );


        return MenteeProfileInfo.from(profile);
    }
}