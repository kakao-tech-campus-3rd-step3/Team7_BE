package com.careerfit.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    MENTEE("ROLE_MENTEE"), MENTO("ROLE_MENTO");

    private final String role;
}
