package com.careerfit.mentoring.dto;

import com.careerfit.mentoring.domain.Mentoring;
import com.careerfit.mentoring.domain.MentoringStatus;

import java.time.LocalDate;

public record MentoringDetailResponse(
    Long id,
    String title,
    String description,
    LocalDate dueDate,
    String mentorName,
    String menteeName,
    MentoringStatus status
) {
    public static MentoringDetailResponse from(Mentoring m) {
        return new MentoringDetailResponse(
            m.getId(),
            m.getTitle(),
            m.getDescription(),
            m.getDueDate(),
            m.getMento().getName(),
            m.getMentee().getName(),
            m.getMentoringStatus()
        );
    }
}
