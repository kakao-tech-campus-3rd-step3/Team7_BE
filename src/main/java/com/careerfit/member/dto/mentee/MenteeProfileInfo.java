package com.careerfit.member.dto.mentee;

import com.careerfit.member.domain.mentee.MenteeProfile;

import java.util.List;
import java.util.stream.Collectors;

public record MenteeProfileInfo(
    String name,
    String email,
    String phoneNumber,
    String university,
    String major,
    Integer graduationYear,
    List<MenteeWishCompanyResponse> wishCompanies,
    List<MenteeWishPositionResponse> wishPositions
) {
    public static MenteeProfileInfo from(MenteeProfile profile) {
        return new MenteeProfileInfo(
            profile.getMember().getName(),
            profile.getMember().getEmail(),
            profile.getMember().getPhoneNumber(),
            profile.getUniversity(),
            profile.getMajor(),
            profile.getGraduationYear(),
            profile.getWishCompanies().stream()
                .map(MenteeWishCompanyResponse::from)
                .collect(Collectors.toList()),
            profile.getWishPositions().stream()
                .map(MenteeWishPositionResponse::from)
                .collect(Collectors.toList())
        );
    }
}


