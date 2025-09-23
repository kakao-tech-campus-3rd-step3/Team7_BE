package com.careerfit.member.dto;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberRole;

// CommentResponse 생성에 사용될 전용 Member dto입니다.
public record MemberInfoResponse(
    Long id,
    String name,
    MemberRole memberRole
) {
    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(member.getId(), member.getName(), member.getMemberRole());
    }
}
