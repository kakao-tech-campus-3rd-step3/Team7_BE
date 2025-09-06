package com.careerfit.document.service;

import com.careerfit.document.domain.Resume;
import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.document.exception.FileException;
import com.careerfit.document.exception.ResumeErrorCode;
import com.careerfit.document.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeFinder resumeFinder;
    private final ResumeRepository resumeRepository;

    public FileInfoResponse getResumeInfo(Long applicationId, Long resumeId){
        Resume resume = resumeFinder.findResumeOrThrow(resumeId);

        if(!resume.getApplication().getId().equals(applicationId)){
            throw new FileException(ResumeErrorCode.RESUME_NOT_MATCHED);
        }

        return FileInfoResponse.fromResume(resume);
    }

    public void deleteResume(Long applicationId, Long resumeId){
        Resume resume = resumeFinder.findResumeOrThrow(resumeId);

        if(!resume.getApplication().getId().equals(applicationId)){
            throw new FileException(ResumeErrorCode.RESUME_NOT_MATCHED);
        }

        resumeRepository.deleteById(resumeId);
    }

}
