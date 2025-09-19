package com.careerfit.auth.dto;

import java.util.Map;
import java.util.Optional;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.auth.exception.AuthErrorCode;
import com.careerfit.global.exception.ApplicationException;

import lombok.Builder;

@Builder
public record OAuthUserInfo(
    String email,
    String name,
    String profileImage,
    String oauthId
) {

    public static OAuthUserInfo of(OAuthProvider oAuthProvider, Map<String, Object> attributes) {
        return switch (oAuthProvider) {
            case KAKAO -> ofKakao(attributes);
            default -> throw new ApplicationException(AuthErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
        };
    }

    public static OAuthUserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = Optional.ofNullable(
                (Map<String, Object>) attributes.get("kakao_account"))
            .orElse(Map.of());

        String email = String.valueOf(kakaoAccount.get("email"));
        String phoneNumber = Optional.ofNullable(String.valueOf(kakaoAccount.get("phone_number")))
            .map(p -> p.replace("+82 ", "0"))
            .orElse(null);

        Map<String, Object> profile = Optional.ofNullable(
                (Map<String, Object>) kakaoAccount.get("profile"))
            .orElse(Map.of());

        String nickname = String.valueOf(profile.get("nickname"));
        String profileImage = String.valueOf(profile.get("profile_image_url"));
        String oauthId = String.valueOf(attributes.get("id"));

        return OAuthUserInfo.builder()
            .email(email)
            .name(nickname)
            .profileImage(profileImage)
            .oauthId(oauthId)
            .build();
    }
}
