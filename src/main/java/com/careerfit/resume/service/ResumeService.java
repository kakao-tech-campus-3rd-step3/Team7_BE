package com.careerfit.resume.service;

import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.resume.domain.Resume;
import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.FileCreateResponse;
import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.resume.exception.ResumeErrorCode;
import com.careerfit.resume.repository.ResumeRepository;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.global.util.DocumentUtil;
import com.careerfit.resume.service.ResumeFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeFinder resumeFinder;
    private final ResumeRepository resumeRepository;
    private final ApplicationFinder applicationFinder;

    @Transactional
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

    // Portfolio 저장
    public FileCreateResponse completeUpload(Long requestApplicationId,
        CompleteUploadRequest request) {

        Long applicationId = DocumentUtil.extractApplicationId(request.uniqueFileName());
        String documentTitle = DocumentUtil.extractDocumentTitle(request.uniqueFileName());
        String originalFileName = DocumentUtil.extractOriginalFileName(request.uniqueFileName());

        // /api/application/{requestApplicationId}/~와 presignedUrl에 담긴 applicationId가 다르면 예외 발생
        if (!applicationId.equals(requestApplicationId)) {
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_UNMATCHED);
        }

        Resume resume = Resume.of(originalFileName, request.uniqueFileName(), documentTitle,
            applicationFinder.getApplicationOrThrow(applicationId));

        resumeRepository.save(resume);

        return FileCreateResponse.fromResume(resume);
    }

    private void verifyApplicationOwnership(Long applicationId, Resume resume) {
        if (!resume.getApplication().getId().equals(applicationId)) {
            throw new ApplicationException(ResumeErrorCode.RESUME_NOT_MATCHED)
                .addErrorInfo("request application Id", applicationId);
        }
    }

}
