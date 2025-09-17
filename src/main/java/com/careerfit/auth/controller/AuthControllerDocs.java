package com.careerfit.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.view.RedirectView;

import com.careerfit.auth.dto.ReissueTokenRequest;
import com.careerfit.auth.dto.TokenInfo;
import com.careerfit.global.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(description = "인증 API", name = "Authentication API")
public interface AuthControllerDocs {

    @Operation(summary = "소셜 로그인", description = "1. 소셜 로그인(카카오)을 위해 카카오 인증 페이지로 리다이렉션합니다.\n"
        + "2. 사용자가 인증 정보를 입력하면 백엔드 서버가 콜백을 받아 기존회원, 신규회원에 따라 적절한 응답값을 반환합니다. ")
    public RedirectView socialLogin(@PathVariable(name = "registrationId") String registrationId);

    @Operation(summary = "토큰 재발급", description = "리프레쉬 토큰을 통해 액세스, 리프레쉬 토큰을 재발급합니다.")
    public ResponseEntity<ApiResponse<TokenInfo>> reissueTokens(
        @Valid @RequestBody ReissueTokenRequest reissueTokenRequest);
}
