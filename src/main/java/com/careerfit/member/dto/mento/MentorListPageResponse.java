package com.careerfit.member.dto.mento;

import java.util.List;

public record MentorListPageResponse(
        PageInfo pageInfo,
        List<MentorListResponse> mentors
) {
    public record PageInfo(
            int page,
            int size,
            long totalElements,
            int totalPages
    ) {
    }
}