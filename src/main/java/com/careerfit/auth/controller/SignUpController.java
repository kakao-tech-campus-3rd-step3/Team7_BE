package com.careerfit.auth.controller;

import com.careerfit.auth.dto.MenteeSignUpRequest;
import com.careerfit.auth.dto.MentoSignUpRequest;
import com.careerfit.auth.dto.SignUpResponse;
import com.careerfit.auth.service.AuthService;
import com.careerfit.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sign-up")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;

    @PostMapping("/mento")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUpMento(@Valid @RequestBody MentoSignUpRequest dto) {
        return ResponseEntity.ok(ApiResponse.success(authService.signUpAsMento(dto)));
    }

    @PostMapping("/mentee")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUpMentee(@Valid @RequestBody MenteeSignUpRequest dto) {
        return ResponseEntity.ok(ApiResponse.success(authService.signUpAsMentee(dto)));
    }

}
