package com.careerfit.coverletter.repository;

import com.careerfit.coverletter.domain.CoverLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoverLetterJpaRepository extends JpaRepository<CoverLetter, Long> {

    Page<CoverLetter> findAllByApplicationId(Long applicationId, Pageable pageable);
}
