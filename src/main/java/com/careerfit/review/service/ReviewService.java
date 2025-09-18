package com.careerfit.review.service;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MentorProfile;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.repository.MemberJpaRepository;
import com.careerfit.member.repository.MentorProfileJpaRepository;
import com.careerfit.review.domain.Review;
import com.careerfit.review.dto.ReviewGetResponse;
import com.careerfit.review.dto.ReviewPatchRequest;
import com.careerfit.review.dto.ReviewPostRequest;
import com.careerfit.review.dto.ReviewPostResponse;
import com.careerfit.review.dto.ReviewUpdateResponse;
import com.careerfit.review.exception.ReviewErrorCode;
import com.careerfit.review.repository.ReviewJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJpaRepository reviewJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final MentorProfileJpaRepository mentorProfileJpaRepository;

    public ReviewPostResponse createReview(Long menteeId, Long mentoId, ReviewPostRequest request) {
        Member mentee = findMemberById(menteeId);
        Member mento = findMemberById(mentoId);

        Review review = Review.create(mentee, mento, request.rating(), request.content());
        Review savedReview = reviewJpaRepository.save(review);

        updateMentoReviewStats(mento);

        return new ReviewPostResponse(savedReview.getId());
    }

    @Transactional(readOnly = true)
    public ReviewGetResponse getReviewsByMento(Long mentoId) {
        MentorProfile mentoProfile = findMentorProfileByMemberId(mentoId);
        List<Review> reviews = reviewJpaRepository.findByMento(mentoProfile.getMember());

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
                mentoProfile.getReviewCount(),
                mentoProfile.getRating(),
                reviewDetails
        );
    }

    public ReviewUpdateResponse updateReview(Long reviewId, Long menteeId,
            ReviewPatchRequest request) {
        Review review = findReviewById(reviewId);
        validateReviewOwner(review, menteeId);

        review.update(request.rating(), request.content());
        updateMentoReviewStats(review.getMento());

        return new ReviewUpdateResponse(review.getId());
    }

    public void deleteReview(Long reviewId, Long menteeId) {
        Review review = findReviewById(reviewId);
        validateReviewOwner(review, menteeId);
        Member mento = review.getMento();

        reviewJpaRepository.delete(review);
        updateMentoReviewStats(mento);
    }

    private void updateMentoReviewStats(Member mento) {
        List<Review> reviews = reviewJpaRepository.findByMento(mento);

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
                .orElseThrow(() -> new ApplicationException(MemberErrorCode.MENTO_PROFILE_NOT_FOUND));
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