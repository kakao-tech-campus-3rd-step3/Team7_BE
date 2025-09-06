package com.careerfit.auth.handler;

import com.careerfit.auth.domain.CustomOAuth2User;
import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.auth.dto.LoginResponse;
import com.careerfit.auth.dto.OAuthUserInfo;
import com.careerfit.auth.utils.JwtUtils;
import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        ;
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();

        OAuthUserInfo oAuthUserInfo = OAuthUserInfo.of(OAuthProvider.from(registrationId), attributes);

        ApiResponse<LoginResponse> apiResponse;
        if (oAuth2User.isNewUser()) {
            apiResponse = ApiResponse.success(LoginResponse.forNewUser(oAuthUserInfo));

        } else {
            Member member = oAuth2User.getMember();
            String accessToken = jwtUtils.createAccessToken(member.getId(), Set.of(member.getMemberRole().getRole()));
            String refreshToken = jwtUtils.createRefreshToken(member.getId());

            // TODO: redis에 Refresh Token 저장...

            LoginResponse loginResponse = LoginResponse.forExistingUser(
                    oAuthUserInfo,
                    member.getMemberRole(),
                    accessToken,
                    refreshToken
            );
            apiResponse = ApiResponse.success(loginResponse);
        }
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
