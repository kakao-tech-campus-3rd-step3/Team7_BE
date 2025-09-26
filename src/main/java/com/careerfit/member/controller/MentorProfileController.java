package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentor.MentorOwnProfileInfo;
import com.careerfit.member.dto.mentor.MentorProfileUpdateRequest;
import com.careerfit.member.service.mentor.MentorProfileQueryService;
import com.careerfit.member.service.mentor.MentorProfileUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorProfileController {

    private final MentorProfileQueryService mentorProfileQueryService;
    private final MentorProfileUpdateService mentorProfileUpdateService;

    @GetMapping("/{mentorId}/profile")
    public ResponseEntity<ApiResponse<MentorOwnProfileInfo>> getMentorProfile(@PathVariable Long mentorId) {
        MentorOwnProfileInfo result = mentorProfileQueryService.getMentorProfile(mentorId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PatchMapping("/{mentorId}/profile")
    public ResponseEntity<ApiResponse<MentorOwnProfileInfo>> updateMentorProfile(
        @PathVariable Long mentorId,
        @RequestBody MentorProfileUpdateRequest request) {
        MentorOwnProfileInfo result = mentorProfileUpdateService.updateMentorProfile(mentorId, request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}

