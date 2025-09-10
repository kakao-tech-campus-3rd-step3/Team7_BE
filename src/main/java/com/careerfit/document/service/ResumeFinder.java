package com.careerfit.document.service;

import com.careerfit.document.domain.Resume;
import com.careerfit.document.exception.ResumeErrorCode;
import com.careerfit.document.repository.ResumeRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeFinder {
    private final ResumeRepository resumeRepository;

    public Resume findResumeOrThrow(Long resumeId){
        return resumeRepository.findById(resumeId)
            .orElseThrow(()-> new ApplicationException(ResumeErrorCode.RESUME_NOT_FOUND));
    }
}
