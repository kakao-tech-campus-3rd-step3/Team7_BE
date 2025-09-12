package com.careerfit.member.repository;

import com.careerfit.member.domain.MentoProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoProfileJpaRepository extends JpaRepository<MentoProfile, Long> {

}