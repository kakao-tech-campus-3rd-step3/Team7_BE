package com.careerfit.mentoring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MentoringCreateRequest(
    @NotNull Long mentorId,
    @NotNull Long documentId,
    @NotBlank String title,
    @NotNull LocalDate dueDate,
    String description
) {
}
