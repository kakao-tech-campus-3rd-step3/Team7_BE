package com.careerfit.mentoring.dto;

import java.time.LocalDate;

public record MentoringCreateRequest(
    Long mentorId,
    Long documentId,
    String title,
    LocalDate dueDate,
    String description
) { }
