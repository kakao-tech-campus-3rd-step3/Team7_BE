package com.careerfit.member.dto.mentee;

import com.careerfit.member.domain.mentee.MenteeWishCompany;

public record MenteeWishCompanyResponse(
    String companyName
) {
    public static MenteeWishCompanyResponse from(MenteeWishCompany entity) {
        return new MenteeWishCompanyResponse(entity.getCompanyName());
    }
}
