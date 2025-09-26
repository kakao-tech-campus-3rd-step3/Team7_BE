package com.careerfit.resume.repository;

import com.careerfit.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

}
