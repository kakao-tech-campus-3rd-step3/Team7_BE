package com.careerfit.document.service;

import com.careerfit.document.domain.Resume;
import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.document.exception.ResumeErrorCode;
import com.careerfit.document.repository.ResumeRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeFinder resumeFinder;
    private final ResumeRepository resumeRepository;

    public FileInfoResponse getResumeInfo(Long applicationId, Long resumeId) {
        Resume resume = resumeFinder.findResumeOrThrow(resumeId);

        verifyApplicationOwnership(applicationId, resume);

        return FileInfoResponse.fromResume(resume);
    }

    public void deleteResume(Long applicationId, Long resumeId) {
        Resume resume = resumeFinder.findResumeOrThrow(resumeId);

        verifyApplicationOwnership(applicationId, resume);

        resumeRepository.deleteById(resumeId);
    }

    private void verifyApplicationOwnership(Long applicationId, Resume resume) {
        if (!resume.getApplication().getId().equals(applicationId)) {
            throw new ApplicationException(ResumeErrorCode.RESUME_NOT_MATCHED)
                .addErrorInfo("request application Id", applicationId);
        }
    }

}
