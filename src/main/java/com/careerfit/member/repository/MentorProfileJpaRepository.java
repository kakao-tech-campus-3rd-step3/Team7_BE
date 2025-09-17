package com.careerfit.member.repository;

import java.util.Optional;
import com.careerfit.member.domain.MentorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorProfileJpaRepository extends JpaRepository<MentorProfile, Long> {

    Optional<MentorProfile> findByMemberId(Long memberId);
}