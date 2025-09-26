package com.careerfit.review.service;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.repository.MemberJpaRepository;
import com.careerfit.member.repository.MentorProfileJpaRepository;
import com.careerfit.review.domain.Review;
import com.careerfit.review.dto.*;
import com.careerfit.review.exception.ReviewErrorCode;
import com.careerfit.review.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJpaRepository reviewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final MentorProfileJpaRepository mentorProfileJpaRepository;

    public ReviewPostResponse createReview(Long menteeId, Long mentorId, ReviewPostRequest request) {
        Member mentee = findMemberById(menteeId);
        Member mentor = findMemberById(mentorId);

        Review review = Review.create(mentee, mentor, request.rating(), request.content());
        Review savedReview = reviewJpaRepository.save(review);

        updateMentoReviewStats(mentor);

        return new ReviewPostResponse(savedReview.getId());
    }

    @Transactional(readOnly = true)
    public ReviewGetResponse getReviewsByMentor(Long mentorId) {
        MentorProfile mentorProfile = findMentorProfileByMemberId(mentorId);
        List<Review> reviews = reviewJpaRepository.findByMentor(mentorProfile.getMember());

        List<ReviewGetResponse.ReviewDetail> reviewDetails = reviews.stream()
            .map(review -> new ReviewGetResponse.ReviewDetail(
                review.getMentee().getId(),
                review.getMentee().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedDate()
            ))
            .toList();

        return new ReviewGetResponse(
            mentorProfile.getReviewCount(),
            mentorProfile.getAverageRating(),
            reviewDetails
        );
    }

    public ReviewUpdateResponse updateReview(Long reviewId, Long menteeId,
                                             ReviewPatchRequest request) {
        Review review = findReviewById(reviewId);
        validateReviewOwner(review, menteeId);

        review.update(request.rating(), request.content());
        updateMentoReviewStats(review.getMentor());

        return new ReviewUpdateResponse(review.getId());
    }

    public void deleteReview(Long reviewId, Long menteeId) {
        Review review = findReviewById(reviewId);
        validateReviewOwner(review, menteeId);
        Member mento = review.getMentor();

        reviewJpaRepository.delete(review);
        updateMentoReviewStats(mento);
    }

    private void updateMentoReviewStats(Member mento) {
        List<Review> reviews = reviewJpaRepository.findByMentor(mento);

        int reviewCount = reviews.size();
        double averageRating = reviews.stream()
            .mapToDouble(Review::getRating)
            .average()
            .orElse(0.0);

        double roundedRating = Math.round(averageRating * 10.0) / 10.0;


        MentorProfile mentorProfile = findMentorProfileByMemberId(mento.getId());
        mentorProfile.updateReviewStats(reviewCount, roundedRating);
    }

    private Member findMemberById(Long memberId) {
        return memberJpaRepository.findById(memberId)
            .orElseThrow(() -> new ApplicationException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private MentorProfile findMentorProfileByMemberId(Long memberId) {
        return mentorProfileJpaRepository.findByMemberId(memberId)
            .orElseThrow(() -> new ApplicationException(MemberErrorCode.MENTOR_PROFILE_NOT_FOUND));
    }

    private Review findReviewById(Long reviewId) {
        return reviewJpaRepository.findById(reviewId)
            .orElseThrow(() -> new ApplicationException(ReviewErrorCode.REVIEW_NOT_FOUND));
    }

    private void validateReviewOwner(Review review, Long currentMemberId) {
        if (!review.getMentee().getId().equals(currentMemberId)) {
            throw new ApplicationException(ReviewErrorCode.FORBIDDEN_ACCESS);
        }
    }
}