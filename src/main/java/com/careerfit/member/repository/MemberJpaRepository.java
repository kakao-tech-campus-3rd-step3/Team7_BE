package com.careerfit.member.repository;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndOauthId(OAuthProvider oAuthProvider, String oauthId);

    Optional<Member> findByEmail(String email);

    Page<Member> findDistinctByMemberRoleAndNameContainsIgnoreCaseOrMentorProfile_CompanyContainsIgnoreCaseOrMentorProfile_JobPositionContainsIgnoreCase(
        MemberRole role,
        String name,
        String company,
        String jobPosition,
        Pageable pageable
    );

    // 멘토 정렬: 평균평점 기준
    Page<Member> findByMemberRoleOrderByMentorProfile_AverageRatingDesc(MemberRole role, Pageable pageable);
}