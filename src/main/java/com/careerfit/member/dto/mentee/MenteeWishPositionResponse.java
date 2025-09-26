package com.careerfit.member.dto.mentee;

import com.careerfit.member.domain.mentee.MenteeWishPosition;

public record MenteeWishPositionResponse(
    String positionName
) {
    public static MenteeWishPositionResponse from(MenteeWishPosition entity) {
        return new MenteeWishPositionResponse(entity.getPositionName());
    }
}
