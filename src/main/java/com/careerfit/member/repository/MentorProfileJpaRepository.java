package com.careerfit.member.repository;

import com.careerfit.member.domain.MentorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoProfileJpaRepository extends JpaRepository<MentorProfile, Long> {

}