package com.careerfit.member.repository;

import com.careerfit.member.domain.mentee.MenteeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeProfileJpaRepository extends JpaRepository<MenteeProfile, Long> {
    MenteeProfile findByMemberId(Long memberId);
}