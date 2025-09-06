package com.careerfit.member.service;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberRole;
import com.careerfit.member.repository.MemberJpaRepository;
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
        return memberFinder.getMemberOrThrow(memberId);
    }

    public Page<Member> getMentorList(String search, Pageable pageable) {
        return memberJpaRepository.findByRoleAndSearch(MemberRole.MENTO, search, pageable);
    }
}