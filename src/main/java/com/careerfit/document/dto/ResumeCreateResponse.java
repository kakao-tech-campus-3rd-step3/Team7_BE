package com.careerfit.document.dto;

import com.careerfit.document.domain.Resume;

public record ResumeCreateResponse(
        Long id,
        String originalFileName,
        String title
) {
    public static ResumeCreateResponse from(Resume resume) {
        return new ResumeCreateResponse(resume.getId(), resume.getOriginalFileName(),
                resume.getTitle());
    }
}
