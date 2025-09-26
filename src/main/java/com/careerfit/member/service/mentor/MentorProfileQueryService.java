package com.careerfit.member.service.mentor;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.dto.mentor.MentorOwnProfileInfo;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentorProfileQueryService {

    private final MemberFinder memberFinder;

    public MentorOwnProfileInfo getMentorProfile(Long mentorId) {
        Member mentor = memberFinder.getMentorOrThrow(mentorId);
        MentorProfile profile = mentor.getMentorProfile();
        if (profile == null) {
            throw new ApplicationException(MemberErrorCode.MENTOR_PROFILE_NOT_FOUND);
        }
        return MentorOwnProfileInfo.from(mentor, profile);
    }
}
