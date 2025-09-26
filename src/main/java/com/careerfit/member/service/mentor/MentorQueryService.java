package com.careerfit.member.service.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.dto.mentor.*;
import com.careerfit.member.service.MemberFinder;
import com.careerfit.review.domain.Review;
import com.careerfit.review.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentorQueryService {

    private final MemberFinder memberFinder;
    private final ReviewJpaRepository reviewRepository;

    public MentorListPageResponse getMentors(String search, int page, int size, String sortBy,
                                             String sortOrder) {

        Sort.Direction direction = Sort.Direction.fromString(
            sortOrder != null ? sortOrder : "DESC");
        Sort sort = Sort.by(direction, sortBy != null ? sortBy : "mentorProfile.averageRating");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Member> mentorPage = memberFinder.getMentorPage(search, pageable);

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
        Member m = memberFinder.getMentorOrThrow(mentorId);
        MentorProfile p = m.getMentorProfile();

        return new MentorHeaderResponse(
            m.getId(),
            m.getName(),
            m.getProfileImageUrl(),
            p.getCompany(),
            p.getJobPosition(),
            p.getAverageRating(),
            p.getReviewCount(),
            p.getCareerYears(),
            p.getMenteeCount()
        );

    }

    public MentorIntroductionResponse getMentorIntroduction(Long mentorId) {
        MentorProfile p = memberFinder.getMentorOrThrow(mentorId).getMentorProfile();

        List<MentorCareerResponse> careerResponses = p.getMentorCareers().stream()
            .map(MentorCareerResponse::from)
            .toList();

        List<MentorEducationResponse> educationResponses = p.getEducations().stream()
            .map(MentorEducationResponse::from)
            .toList();

        List<MentorExpertiseResponse> expertiseResponses = p.getExpertises().stream()
            .map(MentorExpertiseResponse::from)
            .toList();

        List<MentorCertificationResponse> certificationResponses = p.getCertifications().stream()
            .map(MentorCertificationResponse::from)
            .toList();

        return new MentorIntroductionResponse(
            p.getIntroduction(),
            educationResponses,
            expertiseResponses,
            certificationResponses,
            careerResponses
        );
    }


    public MentorReviewResponse getMentorReviews(Long mentorId) {
        Member mentor = memberFinder.getMentorOrThrow(mentorId);
        List<Review> reviews = reviewRepository.findByMentor(mentor);

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