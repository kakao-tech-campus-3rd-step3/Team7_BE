package com.careerfit.coverletter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerfit.coverletter.domain.CoverLetter;

public interface CoverLetterJpaRepository extends JpaRepository<CoverLetter, Long> {

    List<CoverLetter> findAllByApplicationId(Long applicationId);
}
