package com.careerfit.auth.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.careerfit.auth.domain.CustomOAuth2User;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.auth.dto.OAuthUserInfo;
import com.careerfit.auth.dto.TokenInfo;
import com.careerfit.auth.service.AuthService;
import com.careerfit.member.domain.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;
    @Value("${kareer-fit.oauth.redirect-url}")
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();

        OAuthUserInfo oAuthUserInfo = OAuthUserInfo.of(OAuthProvider.from(registrationId),
            attributes);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(
            redirectUrl);
        if (oAuth2User.isNewUser()) {
            builder.queryParam("isNewUser", true)
                .queryParam("oauthId", oAuthUserInfo.oauthId());
        } else {
            Member member = oAuth2User.getMember();
            TokenInfo tokenInfo = authService.issueTokens(member);
            builder.queryParam("isNewUser", false)
                .queryParam("accessToken", tokenInfo.accessToken())
                .queryParam("refreshToken", tokenInfo.refreshToken());
        }

        getRedirectStrategy().sendRedirect(request, response, builder.build().toUriString());
    }
}
