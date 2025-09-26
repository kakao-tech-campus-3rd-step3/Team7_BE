package com.careerfit.auth.controller;

import com.careerfit.auth.dto.ReissueTokenRequest;
import com.careerfit.auth.dto.TokenInfo;
import com.careerfit.auth.service.AuthService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @PostMapping("/login/{registrationId}")
    public RedirectView socialLogin(@PathVariable(name = "registrationId") String registrationId) {
        String authorizationUrl = authService.getAuthorizationUrl(registrationId);
        return new RedirectView(authorizationUrl);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenInfo>> reissueTokens(
        @Valid @RequestBody ReissueTokenRequest reissueTokenRequest) {
        return ResponseEntity.ok(
            ApiResponse.success(authService.reissueTokens(reissueTokenRequest)));
    }

}
