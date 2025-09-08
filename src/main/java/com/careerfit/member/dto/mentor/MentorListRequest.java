package com.careerfit.member.dto.mentor;

public record MentorListRequest(
    String search,
    String sortBy,
    String sortOrder,
    int page,
    int size
) {

}
