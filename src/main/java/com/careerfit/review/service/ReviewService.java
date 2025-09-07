package com.careerfit.review.service;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MentoProfile;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.repository.MemberJpaRepository;
import com.careerfit.member.repository.MentoProfileJpaRepository;
import com.careerfit.review.domain.Review;
import com.careerfit.review.dto.ReviewPostRequest;
import com.careerfit.review.dto.ReviewPostResponse;
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
    private final MentoProfileJpaRepository mentoProfileJpaRepository;

    public ReviewPostResponse createReview(Long menteeId, Long mentoId, ReviewPostRequest request) {
        Member mentee = findMemberById(menteeId);
        Member mento = findMemberById(mentoId);

        Review review = Review.create(mentee, mento, request.rating(), request.content());
        Review savedReview = reviewJpaRepository.save(review);

        updateMentoReviewStats(mento);

        return new ReviewPostResponse(savedReview.getId());
    }

    private void updateMentoReviewStats(Member mento) {
        List<Review> reviews = reviewJpaRepository.findByMento(mento);

        int reviewCount = reviews.size();
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        double roundedRating = Math.round(averageRating * 10.0) / 10.0;

        MentoProfile mentoProfile = findMentoProfileByMemberId(mento.getId());
        mentoProfile.updateReviewStats(reviewCount, roundedRating);
    }

    private Member findMemberById(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private MentoProfile findMentoProfileByMemberId(Long memberId) {
        return mentoProfileJpaRepository.findById(memberId)
                .orElseThrow(
                        () -> new ApplicationException(MemberErrorCode.MENTO_PROFILE_NOT_FOUND));
    }
}