package com.careerfit.attachmentfile.service;

import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.dto.CompleteUploadRequest;
import com.careerfit.attachmentfile.dto.FileCreateResponse;
import com.careerfit.attachmentfile.dto.FileInfoResponse;
import com.careerfit.attachmentfile.repository.AttachmentFileRepository;
import com.careerfit.attachmentfile.exception.AttachmentFileErrorCode;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.global.util.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttachmentFileService {

    private final AttachmentFileFinder attachmentFileFinder;
    private final AttachmentFileRepository AttachmentFileRepository;
    private final ApplicationFinder applicationFinder;

    // 파일 메타 데이터 조회
    @Transactional
    public FileInfoResponse getPortfolioInfo(Long applicationId, Long attachmentFileId) {
        AttachmentFile attachmentFile = attachmentFileFinder.findAttachmentFileOrThrow(attachmentFileId);
        verifyApplicationOwnership(applicationId, attachmentFile);

        return FileInfoResponse.fromAttachmentFile(attachmentFile);
    }

    public void deletePortfolio(Long applicationId, Long attachmentFileId) {
        AttachmentFile attachmentFile = attachmentFileFinder.findAttachmentFileOrThrow(attachmentFileId);
        verifyApplicationOwnership(applicationId, attachmentFile);

        AttachmentFileRepository.deleteById(attachmentFileId);
    }

    // 파일 메타데이터 DB 저장
    public FileCreateResponse completeUpload(Long requestApplicationId,
        CompleteUploadRequest request) {

        Long applicationId = DocumentUtil.extractApplicationId(request.uniqueFileName());
        String documentTitle = DocumentUtil.extractDocumentTitle(request.uniqueFileName());
        String originalFileName = DocumentUtil.extractOriginalFileName(request.uniqueFileName());

        // /api/application/{requestApplicationId}/~와 presignedUrl에 담긴 applicationId가 다르면 예외 발생
        if (!applicationId.equals(requestApplicationId)) {
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_UNMATCHED);
        }

        AttachmentFile attachmentFile = AttachmentFile.of(originalFileName, request.uniqueFileName(),
            documentTitle, applicationFinder.getApplicationOrThrow(applicationId));

        AttachmentFileRepository.save(attachmentFile);

        return FileCreateResponse.fromAttachmentFile(attachmentFile);
    }

    private void verifyApplicationOwnership(Long applicationId, AttachmentFile attachmentFile) {
        if (!attachmentFile.getApplication().getId().equals(applicationId)) {
            throw new ApplicationException(AttachmentFileErrorCode.ATTACHMENT_FILE_NOT_MATCHED)
                .addErrorInfo("request application Id", applicationId);
        }
    }
}
