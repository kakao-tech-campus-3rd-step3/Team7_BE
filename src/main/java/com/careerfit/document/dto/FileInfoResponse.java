package com.careerfit.document.dto;

import com.careerfit.document.domain.DocumentType;
import com.careerfit.document.domain.Portfolio;
import com.careerfit.document.domain.Resume;

public record FileInfoResponse(
    DocumentType documentType,
    Long id,
    String originalFileName,
    String storedFileName,
    String documentTitle,
    Long applicationId
) {
    public static FileInfoResponse fromPortfolio(Portfolio portfolio){
        return new FileInfoResponse(
            DocumentType.PORTFOLIO,
            portfolio.getId(),
            portfolio.getOriginalFileName(),
            portfolio.getStoredFilePath(),
            portfolio.getTitle(),
            portfolio.getApplication().getId()
        );
    }

    public static FileInfoResponse fromResume(Resume resume){
        return new FileInfoResponse(
            DocumentType.RESUME,
            resume.getId(),
            resume.getOriginalFileName(),
            resume.getStoredFilePath(),
            resume.getTitle(),
            resume.getApplication().getId()
        );
    }
}
