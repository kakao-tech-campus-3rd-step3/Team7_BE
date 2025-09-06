package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.MenteeProfileInfo;
import com.careerfit.member.dto.MenteeProfileUpdateRequest;
import com.careerfit.member.service.MenteeProfileQueryService;
import com.careerfit.member.service.MenteeProfileUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentee/profile")
@RequiredArgsConstructor
public class MenteeProfileController {

    private final MenteeProfileQueryService queryService;
    private final MenteeProfileUpdateService updateService;

    @GetMapping
    public ApiResponse<MenteeProfileInfo> getMenteeProfile(@RequestParam Long memberId) {
        return ApiResponse.success(queryService.getMenteeProfile(memberId));
    }

    @PatchMapping
    public ApiResponse<MenteeProfileInfo> updateMenteeProfile(@RequestParam Long memberId,
                                                 @RequestBody MenteeProfileUpdateRequest request) {
        MenteeProfileInfo updatedProfile = updateService.updateMenteeProfile(memberId, request);
        return ApiResponse.success(updatedProfile);
    }
}

