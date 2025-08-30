package com.careerfit.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AuthService {

    public String getAuthorizationUrl(String registrationId) {
        return UriComponentsBuilder
            .fromPath("/oauth2/authorization/{registrationId}")
            .buildAndExpand(registrationId)
            .toUriString();
    }
}
