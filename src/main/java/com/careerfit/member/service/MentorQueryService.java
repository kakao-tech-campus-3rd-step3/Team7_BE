package com.careerfit.member.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MentorProfile;
import com.careerfit.member.dto.mentor.MentorCareerResponse;
import com.careerfit.member.dto.mentor.MentorHeaderResponse;
import com.careerfit.member.dto.mentor.MentorIntroductionResponse;
import com.careerfit.member.dto.mentor.MentorListPageResponse;
import com.careerfit.member.dto.mentor.MentorListResponse;
import com.careerfit.member.dto.mentor.MentorReviewResponse;
import com.careerfit.review.domain.Review;
import com.careerfit.review.domain.repository.ReviewJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentorQueryService {

    private final MentorFinder mentorFinder;
    private final ReviewJpaRepository reviewRepository;

    public MentorListPageResponse getMentors(String search, int page, int size, String sortBy,
        String sortOrder) {

        Sort.Direction direction = Sort.Direction.fromString(
            sortOrder != null ? sortOrder : "DESC");
        Sort sort = Sort.by(direction, sortBy != null ? sortBy : "mentoProfile.rating");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Member> mentorPage = mentorFinder.getMentorList(search != null ? search : "",
            pageable);

        List<MentorListResponse> mentorList = mentorPage.stream()
            .map(MentorListResponse::from)
            .toList();

        return new MentorListPageResponse(
            new MentorListPageResponse.PageInfo(
                mentorPage.getNumber(),
                mentorPage.getSize(),
                mentorPage.getTotalElements(),
                mentorPage.getTotalPages()
            ),
            mentorList
        );
    }

    public MentorHeaderResponse getMentorHeader(Long mentorId) {
        Member m = mentorFinder.getMentorById(mentorId);
        MentorProfile p = m.getMentoProfile();

        return new MentorHeaderResponse(
            m.getId(),
            m.getName(),
            p.getCompany(),
            p.getJobPosition(),
            p.getRating(),
            p.getReviewCount(),
            p.getCareerYears(),
            p.getMenteeCount()
        );

    }

    public MentorIntroductionResponse getMentorIntroduction(Long mentorId) {
        MentorProfile p = mentorFinder.getMentorById(mentorId).getMentoProfile();

        List<MentorCareerResponse> careerResponses = p.getMentoCareers().stream()
            .map(MentorCareerResponse::from)
            .toList();

        return new MentorIntroductionResponse(
            p.getIntroduction(),
            new ArrayList<>(p.getEducations()),
            new ArrayList<>(p.getExpertises()),
            new ArrayList<>(p.getCertifications()),
            careerResponses
        );
    }

    public MentorReviewResponse getMentorReviews(Long mentorId) {
        Member mentor = mentorFinder.getMentorById(mentorId);
        List<Review> reviews = reviewRepository.findByMento(mentor);

        List<MentorReviewResponse.ReviewDetail> reviewDetails = reviews.stream()
            .map(r -> new MentorReviewResponse.ReviewDetail(
                r.getMentee().getId(),
                r.getMentee().getName(),
                r.getRating(),
                r.getContent(),
                r.getCreatedDate()
            ))
            .toList();

        double avgRating = reviews.stream()
            .mapToDouble(Review::getRating)
            .average()
            .orElse(0.0);

        return new MentorReviewResponse(reviews.size(), avgRating, reviewDetails);
    }
}