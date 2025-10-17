package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentor.MentorOwnProfileInfo;
import com.careerfit.member.dto.mentor.MentorProfileUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "멘토 프로필 API", description = "멘토 프로필 조회 및 수정")
public interface MentorProfileControllerDocs {

    @Operation(summary = "멘토 프로필 조회", description = "멘토 ID로 프로필 정보를 조회합니다.")
    ResponseEntity<ApiResponse<MentorOwnProfileInfo>> getMentorProfile(Long mentorId);

    @Operation(summary = "멘토 프로필 수정", description = "멘토 프로필을 수정합니다.")
    ResponseEntity<ApiResponse<MentorOwnProfileInfo>> updateMentorProfile(Long mentorId, MentorProfileUpdateRequest request);
}
