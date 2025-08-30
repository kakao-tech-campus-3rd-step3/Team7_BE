package com.careerfit.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerfit.auth.dto.SignUpResponse;
import com.careerfit.auth.service.AuthService;
import com.careerfit.global.dto.ApiResponse;
import com.careerfit.auth.dto.MenteeSignUpRequest;
import com.careerfit.auth.dto.MentoSignUpRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sign-up")
@RequiredArgsConstructor
public class SignUpController {

    private final AuthService authService;

    @PostMapping("/mento")
    public ApiResponse<SignUpResponse> signUpMento(@Valid @RequestBody MentoSignUpRequest dto){
        return ApiResponse.success(authService.signUpAsMento(dto));
    }

    @PostMapping("/mentee")
    public ApiResponse<SignUpResponse> signUpMentee(@Valid @RequestBody MenteeSignUpRequest dto){
        return ApiResponse.success(authService.signUpAsMentee(dto));
    }

}
