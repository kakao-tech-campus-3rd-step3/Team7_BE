package com.careerfit.member.exception;

import com.careerfit.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum MemberErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "회원이 존재하지 않습니다."),
    MENTEE_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-002", "멘티를 찾을 수 없습니다."),
    MENTOR_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-003", "멘토를 찾을 수 없습니다."),
    MENTOR_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-004", "해당 멘토 프로필을 찾을 수 없습니다."),
    MENTEE_PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-005", "해당 멘티 프로필을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
