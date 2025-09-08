package com.careerfit.member.dto.mento;

public record MentorListRequest(
    String search,
    String sortBy,
    String sortOrder,
    int page,
    int size
) {

}
