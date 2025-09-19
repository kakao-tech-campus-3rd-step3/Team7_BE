package com.careerfit.mentoring.dto;

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
}
