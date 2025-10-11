package com.careerfit.application.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ApplicationListResponse(
    List<ApplicationSummaryResponse> applications, LocalDateTime nextCursor, boolean hasNext
) {

}
