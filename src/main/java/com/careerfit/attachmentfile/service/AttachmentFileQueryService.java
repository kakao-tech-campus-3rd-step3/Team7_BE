package com.careerfit.attachmentfile.service;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.dto.FileInfoResponse;
import com.careerfit.attachmentfile.repository.AttachmentFileRepository;
import com.careerfit.document.domain.DocumentType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class AttachmentFileQueryService {

    private final AttachmentFileRepository attachmentFileRepository;
    private final AttachmentFileFinder attachmentFileFinder;

    // 단건 조회
    public FileInfoResponse getFileInfo(Long applicationId, Long attachmentFileId) {
        AttachmentFile attachmentFile = attachmentFileFinder.findAttachmentFileOrThrow(
            attachmentFileId);
        AttachmentFileVerifier.verifyApplicationOwnership(applicationId, attachmentFile);

        return FileInfoResponse.fromAttachmentFile(attachmentFile);
    }

    // 리스트 조회
    public List<FileInfoResponse> getFileInfoList(Long applicationId, DocumentType documentType) {
        return attachmentFileRepository.findAllByApplicationId(applicationId)
            .stream()
            .filter(each -> each.getAttachmentFileType().equals(documentType))
            .map(FileInfoResponse::fromAttachmentFile)
            .toList();
    }
}
