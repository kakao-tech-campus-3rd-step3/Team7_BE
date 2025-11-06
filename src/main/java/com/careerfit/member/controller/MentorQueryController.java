package com.careerfit.member.controller;

import com.careerfit.global.dto.ApiResponse;
import com.careerfit.member.dto.mentor.MentorDashboardItem;
import com.careerfit.member.dto.mentor.MentorHeaderResponse;
import com.careerfit.member.dto.mentor.MentorIntroductionResponse;
import com.careerfit.member.dto.mentor.MentorListPageResponse;
import com.careerfit.member.dto.mentor.MentorReviewResponse;
import com.careerfit.member.service.mentor.MentorQueryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mentors")
public class MentorQueryController implements MentorQueryControllerDocs {

    private final MentorQueryService mentorQueryService;

    public MentorQueryController(MentorQueryService mentorQueryService) {
        this.mentorQueryService = mentorQueryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<MentorListPageResponse>> getMentors(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String sortBy,
        @RequestParam(required = false) String sortOrder,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        MentorListPageResponse result = mentorQueryService.getMentors(search, page, size, sortBy,
            sortOrder);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{mentorId}/header")
    public ResponseEntity<ApiResponse<MentorHeaderResponse>> getMentorHeader(@PathVariable Long mentorId) {
        MentorHeaderResponse result = mentorQueryService.getMentorHeader(mentorId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }


    @GetMapping("/{mentorId}/introduction")
    public ResponseEntity<ApiResponse<MentorIntroductionResponse>> getMentorIntroduction(
        @PathVariable Long mentorId) {
        MentorIntroductionResponse result = mentorQueryService.getMentorIntroduction(mentorId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{mentorId}/reviews")
    public ResponseEntity<ApiResponse<MentorReviewResponse>> getMentorReviews(@PathVariable Long mentorId) {
        MentorReviewResponse result = mentorQueryService.getMentorReviews(mentorId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    // mentor dashboard 리스트 조회
    @GetMapping("/{mentorId}/dashboard")
    public ResponseEntity<ApiResponse<List<MentorDashboardItem>>> getMentorDashboard(
        @PathVariable Long mentorId
    ) {
        List<MentorDashboardItem> result = mentorQueryService.getMentorDashboardItems(mentorId);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}

