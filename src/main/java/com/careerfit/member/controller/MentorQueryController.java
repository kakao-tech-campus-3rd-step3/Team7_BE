package com.careerfit.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentor.MentorHeaderResponse;
import com.careerfit.member.dto.mentor.MentorIntroductionResponse;
import com.careerfit.member.dto.mentor.MentorListPageResponse;
import com.careerfit.member.dto.mentor.MentorReviewResponse;
import com.careerfit.member.service.MentorQueryService;

@RestController
@RequestMapping("/api")
public class MentorQueryController {

    private final MentorQueryService mentorQueryService;

    public MentorQueryController(MentorQueryService mentorQueryService) {
        this.mentorQueryService = mentorQueryService;
    }

    @GetMapping("/mentors")
    public ApiResponse<MentorListPageResponse> getMentors(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String sortBy,
        @RequestParam(required = false) String sortOrder,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        MentorListPageResponse result = mentorQueryService.getMentors(search, page, size, sortBy,
            sortOrder);
        return ApiResponse.success(result);
    }

    @GetMapping("/mentors/{mentorId}/header")
    public ApiResponse<MentorHeaderResponse> getMentorHeader(@PathVariable Long mentorId) {
        MentorHeaderResponse result = mentorQueryService.getMentorHeader(mentorId);
        return ApiResponse.success(result);
    }

    @GetMapping("/mentors/{mentorId}/introduction")
    public ApiResponse<MentorIntroductionResponse> getMentorIntroduction(
        @PathVariable Long mentorId) {
        MentorIntroductionResponse result = mentorQueryService.getMentorIntroduction(mentorId);
        return ApiResponse.success(result);
    }

    @GetMapping("/mentors/{mentorId}/reviews")
    public ApiResponse<MentorReviewResponse> getMentorReviews(@PathVariable Long mentorId) {
        MentorReviewResponse result = mentorQueryService.getMentorReviews(mentorId);
        return ApiResponse.success(result);
    }

}

