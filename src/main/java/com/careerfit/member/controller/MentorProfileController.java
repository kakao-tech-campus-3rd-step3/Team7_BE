package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentor.MentorOwnProfileInfo;
import com.careerfit.member.dto.mentor.MentorProfileUpdateRequest;
import com.careerfit.member.service.mentor.MentorProfileQueryService;
import com.careerfit.member.service.mentor.MentorProfileUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mentor/profile")
@RequiredArgsConstructor
public class MentorProfileController {

    private final MentorProfileQueryService mentorProfileQueryService;
    private final MentorProfileUpdateService mentorProfileUpdateService;

    @GetMapping
    public ApiResponse<MentorOwnProfileInfo> getMentorProfile(@RequestParam Long memberId) {
        MentorOwnProfileInfo profile = mentorProfileQueryService.getMentorProfile(memberId);
        return ApiResponse.success(profile);
    }

    @PatchMapping
    public ApiResponse<MentorOwnProfileInfo> updateMentorProfile(@RequestParam Long memberId,
                                                                 @RequestBody MentorProfileUpdateRequest request) {
        MentorOwnProfileInfo updatedProfile = mentorProfileUpdateService.updateMentorProfile(memberId, request);
        return ApiResponse.success(updatedProfile);
    }
}
