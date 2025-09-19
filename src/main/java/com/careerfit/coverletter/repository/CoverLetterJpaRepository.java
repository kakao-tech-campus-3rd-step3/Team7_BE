package com.careerfit.coverletter.repository;

import com.careerfit.coverletter.domain.CoverLetter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoverLetterJpaRepository extends JpaRepository<CoverLetter, Long> {

    List<CoverLetter> findAllByApplicationId(Long applicationId);
}
