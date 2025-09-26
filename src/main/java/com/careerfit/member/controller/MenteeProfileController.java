package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.dto.mentee.MenteeProfileUpdateRequest;
import com.careerfit.member.service.mentee.MenteeProfileQueryService;
import com.careerfit.member.service.mentee.MenteeProfileUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentee/profile")
@RequiredArgsConstructor
public class MenteeProfileController {

    private final MenteeProfileQueryService menteeProfileQueryService;
    private final MenteeProfileUpdateService menteeProfileUpdateService;

    @GetMapping
    public ResponseEntity<ApiResponse<MenteeProfileInfo>> getMenteeProfile(@RequestParam Long memberId) {
        return ResponseEntity.ok(ApiResponse.success(menteeProfileQueryService.getMenteeProfile(memberId)));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<MenteeProfileInfo>> updateMenteeProfile(@RequestParam Long memberId, @RequestBody MenteeProfileUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(menteeProfileUpdateService.updateMenteeProfile(memberId, request)));
    }

}

