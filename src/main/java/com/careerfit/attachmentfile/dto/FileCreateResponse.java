package com.careerfit.attachmentfile.dto;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.document.domain.DocumentType;

public record FileCreateResponse(
    Long id,
    String originalFileName,
    String title,
    DocumentType documentType
) {

    public static FileCreateResponse fromAttachmentFile(AttachmentFile attachmentFile) {
        return new FileCreateResponse(
            attachmentFile.getId(),
            attachmentFile.getOriginalFileName(),
            attachmentFile.getTitle(),
            attachmentFile.getDocumentType());
    }

}
