package com.careerfit.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    MENTEE("ROLE_MENTEE"), MENTOR("ROLE_MENTOR");

    private final String role;
}
