package com.careerfit.auth.dto;

import com.careerfit.member.domain.MemberRole;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponse(
    boolean isExistingUser,
    OAuthUserInfo oAuthUserInfo,
    MemberRole memberType,
    TokenInfo tokenInfo
) {

    public static LoginResponse forNewUser(OAuthUserInfo oAuthUserInfo) {
        return new LoginResponse(false, oAuthUserInfo, null, null);
    }

    public static LoginResponse forExistingUser(OAuthUserInfo oAuthUserInfo, MemberRole memberRole,
        TokenInfo tokenInfo) {
        return new LoginResponse(true, oAuthUserInfo, memberRole, tokenInfo);
    }
}
