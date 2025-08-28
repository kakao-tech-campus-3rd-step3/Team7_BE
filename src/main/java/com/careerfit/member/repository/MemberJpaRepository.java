package com.careerfit.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerfit.member.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}
