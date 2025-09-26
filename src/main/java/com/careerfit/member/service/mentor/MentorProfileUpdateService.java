package com.careerfit.member.service.mentor;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorCertification;
import com.careerfit.member.domain.mentor.MentorEducation;
import com.careerfit.member.domain.mentor.MentorExpertise;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.dto.mentor.MentorOwnProfileInfo;
import com.careerfit.member.dto.mentor.MentorProfileUpdateRequest;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorProfileUpdateService {

    private final MemberFinder memberFinder;

    public MentorOwnProfileInfo updateMentorProfile(Long mentorId, MentorProfileUpdateRequest request) {

        Member mentor = memberFinder.getMentorOrThrow(mentorId);

        MentorProfile profile = mentor.getMentorProfile();
        if (profile == null) {
            throw new ApplicationException(MemberErrorCode.MENTOR_PROFILE_NOT_FOUND);
        }

        if (request.name() != null) mentor.setName(request.name());
        if (request.email() != null) mentor.setEmail(request.email());
        if (request.phoneNumber() != null) mentor.setPhoneNumber(request.phoneNumber());

        List<MentorEducation> educations = null;
        if (request.educations() != null) {
            educations = request.educations().stream()
                .map(e -> MentorEducation.builder()
                    .schoolName(e.schoolName())
                    .major(e.major())
                    .startYear(e.startYear())
                    .endYear(e.endYear())
                    .build())
                .toList();
        }

        List<MentorCertification> certifications = null;
        if (request.certifications() != null) {
            certifications = request.certifications().stream()
                .map(c -> MentorCertification.builder()
                    .certificateName(c.certificateName())
                    .build())
                .toList();
        }
        List<MentorExpertise> expertises = null;
        if (request.expertises() != null) {
            expertises = request.expertises().stream()
                .map(ex -> MentorExpertise.builder()
                    .expertiseName(ex.expertiseName())
                    .build())
                .toList();
        }

        profile.updateProfile(
            request.careerYears(),
            request.company(),
            request.jobPosition(),
            request.employmentCertificate(),
            certifications,
            educations,
            expertises,
            request.introduction()
        );

        return MentorOwnProfileInfo.from(mentor, profile);
    }
}
