package com.careerfit.mentoring.repository;

import com.careerfit.mentoring.domain.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentoringJpaRepository extends JpaRepository<Mentoring, Long> {
    List<Mentoring> findByMenteeId(Long menteeId);
}