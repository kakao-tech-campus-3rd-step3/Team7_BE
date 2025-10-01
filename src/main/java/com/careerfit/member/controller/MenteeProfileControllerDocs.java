package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.dto.mentee.MenteeProfileUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "멘티 프로필 API", description = "멘티 프로필 조회 및 수정")
public interface MenteeProfileControllerDocs {

    @Operation(summary = "멘티 프로필 조회", description = "멘티 ID로 프로필 정보를 조회합니다.")
    ResponseEntity<ApiResponse<MenteeProfileInfo>> getMenteeProfile(Long memberId);

    @Operation(summary = "멘티 프로필 수정", description = "멘티 프로필을 수정합니다.")
    ResponseEntity<ApiResponse<MenteeProfileInfo>> updateMenteeProfile(Long memberId, MenteeProfileUpdateRequest request);
}

