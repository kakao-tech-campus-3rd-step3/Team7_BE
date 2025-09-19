package com.careerfit.attachmentfile.dto;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.document.domain.DocumentType;

public record FileInfoResponse(
    DocumentType documentType,
    Long id,
    String originalFileName,
    String storedFileName,
    String documentTitle,
    Long applicationId
) {

    public static FileInfoResponse fromAttachmentFile(AttachmentFile attachmentFile) {
        return new FileInfoResponse(
            attachmentFile.getDocumentType(),
            attachmentFile.getId(),
            attachmentFile.getOriginalFileName(),
            attachmentFile.getStoredFilePath(),
            attachmentFile.getTitle(),
            attachmentFile.getApplication().getId()
        );
    }
}
