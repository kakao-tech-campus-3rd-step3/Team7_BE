package com.careerfit.auth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.careerfit.auth.domain.CustomUserDetails;
import com.careerfit.auth.exception.AuthErrorCode;
import com.careerfit.auth.service.AuthService;
import com.careerfit.global.exception.ApplicationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final AuthService authService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication == null) {
            throw new ApplicationException(AuthErrorCode.UNAUTHORIZED);
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getUserId();

        authService.logout(userId);
    }
}
