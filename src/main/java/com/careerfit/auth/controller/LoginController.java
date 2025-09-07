package com.careerfit.auth.controller;

import com.careerfit.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;

    @PostMapping("/{registrationId}")
    public RedirectView socialLogin(@PathVariable(name = "registrationId") String registrationId) {
        String authorizationUrl = authService.getAuthorizationUrl(registrationId);
        return new RedirectView(authorizationUrl);
    }
}
