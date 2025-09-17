package com.careerfit.resume.service;

import com.careerfit.resume.domain.Resume;
import com.careerfit.resume.exception.ResumeErrorCode;
import com.careerfit.resume.repository.ResumeRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ResumeFinder {

    private final ResumeRepository resumeRepository;

    public Resume findResumeOrThrow(Long resumeId) {
        return resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ApplicationException(ResumeErrorCode.RESUME_NOT_FOUND));
    }
}
