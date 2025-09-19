package com.careerfit.document.dto;

import com.careerfit.document.domain.DocumentType;
import com.careerfit.document.domain.Portfolio;
import com.careerfit.document.domain.Resume;

public record FileCreateResponse(
    Long id,
    String originalFileName,
    String title,
    DocumentType documentType
) {
    public static FileCreateResponse fromPortfolio(Portfolio portfolio) {
        return new FileCreateResponse(
            portfolio.getId(),
            portfolio.getOriginalFileName(),
            portfolio.getTitle(),
            DocumentType.PORTFOLIO);
    }

    public static FileCreateResponse fromResume(Resume resume) {
        return new FileCreateResponse(
            resume.getId(),
            resume.getOriginalFileName(),
            resume.getTitle(),
            DocumentType.RESUME);
    }
}
