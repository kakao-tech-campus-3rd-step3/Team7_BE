package com.careerfit.attachmentfile.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.domain.AttachmentFileType;
import com.careerfit.attachmentfile.repository.AttachmentFileRepository;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.global.util.DocumentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachmentFileCommandService {

    private final AttachmentFileFinder attachmentFileFinder;
    private final AttachmentFileRepository attachmentFileRepository;
    private final ApplicationFinder applicationFinder;

    // 파일 메타 데이터 저장
    public void saveFile(Long requestApplicationId, String uniqueFileName,
        AttachmentFileType attachmentFileType) {

        Long applicationId = DocumentUtil.extractApplicationId(uniqueFileName);
        String documentTitle = DocumentUtil.extractDocumentTitle(uniqueFileName);
        String originalFileName = DocumentUtil.extractOriginalFileName(uniqueFileName);

        // /api/application/{requestApplicationId}/~와 presignedUrl에 담긴 applicationId가 다르면 예외 발생
        if (!applicationId.equals(requestApplicationId)) {
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_UNMATCHED);
        }

        Application application = applicationFinder.getApplicationOrThrow(applicationId);
        AttachmentFile attachmentFile = AttachmentFile.of(originalFileName, uniqueFileName,
            documentTitle, application, attachmentFileType);
        attachmentFileRepository.save(attachmentFile);
    }

    // 파일 메타 데이터 삭제
    public void deleteFile(Long applicationId, Long attachmentFileId) {
        AttachmentFile attachmentFile = attachmentFileFinder.findAttachmentFileOrThrow(
            attachmentFileId);
        AttachmentFileVerifier.verifyApplicationOwnership(applicationId, attachmentFile);

        attachmentFileRepository.deleteById(attachmentFileId);
    }
}
