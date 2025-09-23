package com.careerfit.member.service.mentee;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentee.MenteeProfile;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.exception.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenteeProfileQueryService {

    private final MenteeFinder menteeFinder;

    public MenteeProfileInfo getMenteeProfile(Long mentorId) {
        Member mentee = menteeFinder.getMenteeById(mentorId);
        MenteeProfile profile = mentee.getMenteeProfile();
        if (profile == null) {
            throw new ApplicationException(MemberErrorCode.MENTEE_PROFILE_NOT_FOUND);
        }
        return MenteeProfileInfo.from(profile);
    }
}
