package com.careerfit.member.repository;

import com.careerfit.member.domain.mentor.MentorProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentorProfileJpaRepository extends JpaRepository<MentorProfile, Long> {

    @EntityGraph(attributePaths= {
            "certifications",
            "educations",
            "expertises",
            "mentorCareers"
    })
    Optional<MentorProfile> findByMemberId(Long memberId);
}