package com.careerfit.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentee.MenteeProfileInfo;
import com.careerfit.member.dto.mentee.MenteeProfileUpdateRequest;
import com.careerfit.member.service.MenteeProfileQueryService;
import com.careerfit.member.service.MenteeProfileUpdateService;

import lombok.RequiredArgsConstructor;

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
        return ApiResponse.success(updateService.updateMenteeProfile(memberId, request));
    }
}

