package com.careerfit.member.service.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberRole;
import com.careerfit.member.repository.MemberJpaRepository;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorFinder {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberFinder memberFinder;

    public Member getMentorById(Long memberId) {
        return memberFinder.getMentorOrThrow(memberId);
    }

    public Page<Member> getMentorList(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            // 검색어가 없으면 정렬 기준만 적용
            return memberJpaRepository.findByMemberRoleOrderByMentorProfile_AverageRatingDesc(MemberRole.MENTOR, pageable);
        }

        // 검색어가 있으면 이름, 회사, 직책 필드 검색
        return memberJpaRepository
            .findDistinctByMemberRoleAndNameContainsIgnoreCaseOrMentorProfile_CompanyContainsIgnoreCaseOrMentorProfile_JobPositionContainsIgnoreCase(
                MemberRole.MENTOR,
                search, search, search,
                pageable
            );
    }
}
