package com.careerfit.member.service.mentee;

import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenteeFinder {

    private final MemberFinder memberFinder;

    public Member getMenteeById(Long memberId) {
        return memberFinder.getMenteeOrThrow(memberId);
    }
}
