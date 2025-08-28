package com.careerfit.auth.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public record KakaoOauthProperties(
    String clientId,
    String clientSecret,
    String authorizeUri,
    String redirectUri,
    Urls urls
) {

    public record Urls(
        String baseAuth,
        String baseApi,
        String token,
        String authorize,
        String userInfo,
        String unlink
    ) {

        public String getTokenUrl() {
            return baseAuth + token;
        }

        public String getAuthorizeUrl() {
            return baseAuth + authorize;
        }

        public String getUserInfoUrl() {
            return baseApi + userInfo;
        }

        public String getUnlinkUrl() {
            return baseApi + unlink;
        }
    }
}
