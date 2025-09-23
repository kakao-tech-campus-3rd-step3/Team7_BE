package com.careerfit.attachmentfile.dto;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.document.domain.DocumentType;

public record FileInfoResponse(
    DocumentType attachmentFileType,
    Long id,
    String originalFileName,
    String storedFilePath,
    String documentTitle,
    Long applicationId,
    String presignedUrl
) {

    // 메타데이터 조회용
    public static FileInfoResponse fromAttachmentFile(AttachmentFile attachmentFile) {
        return new FileInfoResponse(
            attachmentFile.getAttachmentFileType(),
            attachmentFile.getId(),
            attachmentFile.getOriginalFileName(),
            attachmentFile.getStoredFilePath(),
            attachmentFile.getTitle(),
            attachmentFile.getApplication().getId(),
            null
        );
    }

    // presignedUrl 발급용
    public static FileInfoResponse withPresignedUrl(AttachmentFile attachmentFile, String presignedUrl){
        return new FileInfoResponse(
            attachmentFile.getAttachmentFileType(),
            attachmentFile.getId(),
            attachmentFile.getOriginalFileName(),
            attachmentFile.getStoredFilePath(),
            attachmentFile.getTitle(),
            attachmentFile.getApplication().getId(),
            presignedUrl
        );
    }

}
