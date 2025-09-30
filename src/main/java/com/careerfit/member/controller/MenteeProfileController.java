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
@RequestMapping("/api/mentees")
@RequiredArgsConstructor
public class MenteeProfileController implements MenteeProfileControllerDocs {

    private final MenteeProfileQueryService menteeProfileQueryService;
    private final MenteeProfileUpdateService menteeProfileUpdateService;

    @GetMapping("/{memberId}/profile")
    public ResponseEntity<ApiResponse<MenteeProfileInfo>> getMenteeProfile(@PathVariable Long memberId) {
        MenteeProfileInfo result = menteeProfileQueryService.getMenteeProfile(memberId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PatchMapping("/{memberId}/profile")
    public ResponseEntity<ApiResponse<MenteeProfileInfo>> updateMenteeProfile(@PathVariable Long memberId,
                                                                              @RequestBody MenteeProfileUpdateRequest request) {
        MenteeProfileInfo result = menteeProfileUpdateService.updateMenteeProfile(memberId, request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}


