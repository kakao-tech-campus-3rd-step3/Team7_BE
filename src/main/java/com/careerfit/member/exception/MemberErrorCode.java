package com.careerfit.member.exception;

import org.springframework.http.HttpStatus;

import com.careerfit.global.exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "회원이 존재하지 않습니다."),
    MENTO_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-002", "해당 멘토 프로필을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
