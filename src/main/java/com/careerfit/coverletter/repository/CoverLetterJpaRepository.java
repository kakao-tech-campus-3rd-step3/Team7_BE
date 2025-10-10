package com.careerfit.coverletter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.careerfit.coverletter.domain.CoverLetter;


public interface CoverLetterJpaRepository extends JpaRepository<CoverLetter, Long> {

    Page<CoverLetter> findAllByApplicationId(Long applicationId, Pageable pageable);
}
