package com.careerfit.auth.controller;

import com.careerfit.auth.dto.MenteeSignUpRequest;
import com.careerfit.auth.dto.MentorSignUpRequest;
import com.careerfit.auth.dto.SignUpResponse;
import com.careerfit.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(description = "회원가입 API", name = "SignUp API")
public interface SignUpControllerDocs {

    @Operation(summary = "멘토 회원가입", description = "소셜 로그인 호출 후 신규 회원이 멘토 회원가입을 요청합니다.")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUpMentor(
        @Valid @RequestBody MentorSignUpRequest dto);

    @Operation(summary = "멘티 회원가입", description = "소셜 로그인 호출 후 신규 회원이 멘티 회원가입을 요청합니다.")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUpMentee(
        @Valid @RequestBody MenteeSignUpRequest dto);

}
