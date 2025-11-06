package com.careerfit.member.service.mentor;

import com.careerfit.document.repository.DocumentRepository;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.dto.mentor.MentorCareerResponse;
import com.careerfit.member.dto.mentor.MentorCertificationResponse;
import com.careerfit.member.dto.mentor.MentorDashboardItem;
import com.careerfit.member.dto.mentor.MentorEducationResponse;
import com.careerfit.member.dto.mentor.MentorExpertiseResponse;
import com.careerfit.member.dto.mentor.MentorHeaderResponse;
import com.careerfit.member.dto.mentor.MentorIntroductionResponse;
import com.careerfit.member.dto.mentor.MentorListPageResponse;
import com.careerfit.member.dto.mentor.MentorListResponse;
import com.careerfit.member.dto.mentor.MentorReviewResponse;
import com.careerfit.member.service.MemberFinder;
import com.careerfit.review.domain.Review;
import com.careerfit.review.repository.ReviewJpaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentorQueryService {

    private final MemberFinder memberFinder;
    private final ReviewJpaRepository reviewRepository;
    private final DocumentRepository documentRepository;

    @Cacheable(
            value = "mentorList",
            key = "(#search != null ? #search : 'null') + '_' + #page + '_' + #size + '_' + #sortBy + '_' + #sortOrder",
            condition = "#page < 10"
    )
    public MentorListPageResponse getMentors(String search, int page, int size, String sortBy, String sortOrder) {
        if (page >= 100) {
            long totalCount = (search != null && !search.isBlank()) ? 10_000L : 999_998L;
            return new MentorListPageResponse(
                    new MentorListPageResponse.PageInfo(page, size, totalCount, 100),
                    List.of()
            );
        }

        Sort sort = createSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MentorListResponse> mentorPage = memberFinder.getMentorPage(search, pageable);

        return new MentorListPageResponse(
                new MentorListPageResponse.PageInfo(
                        mentorPage.getNumber(),
                        mentorPage.getSize(),
                        mentorPage.getTotalElements(),
                        Math.min(mentorPage.getTotalPages(), 100)
                ),
                mentorPage.getContent()
        );
    }

    private Sort createSort(String sortBy, String sortOrder) {
        String validSortBy = switch (sortBy == null ? "" : sortBy.toLowerCase()) {
            case "rating", "averagerating" -> "mentorProfile.averageRating";
            case "career", "careeryears" -> "mentorProfile.careerYears";
            case "review", "reviewcount" -> "mentorProfile.reviewCount";
            case "mentee", "menteecount" -> "mentorProfile.menteeCount";
            case "name" -> "name";
            default -> "mentorProfile.averageRating";
        };

        Sort.Direction direction = (sortOrder != null && sortOrder.equalsIgnoreCase("ASC"))
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return Sort.by(direction, validSortBy);
    }

    @Cacheable(value = "mentorHeader", key = "#mentorId")
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
                p.getMenteeCount(),
                p.getPricePerSession()
        );
    }

    @Cacheable(value = "mentorIntro", key = "#mentorId")
    public MentorIntroductionResponse getMentorIntroduction(Long mentorId) {
        MentorProfile p = memberFinder.getMentorOrThrow(mentorId).getMentorProfile();

        return new MentorIntroductionResponse(
                p.getIntroduction(),
                p.getEducations().stream().map(MentorEducationResponse::from).toList(),
                p.getExpertises().stream().map(MentorExpertiseResponse::from).toList(),
                p.getCertifications().stream().map(MentorCertificationResponse::from).toList(),
                p.getMentorCareers().stream().map(MentorCareerResponse::from).toList()
        );
    }

    @Cacheable(value = "mentorReviews", key = "#mentorId")
    public MentorReviewResponse getMentorReviews(Long mentorId) {
        Member mentor = memberFinder.getMentorOrThrow(mentorId);
        List<Review> reviews = reviewRepository.findByMentor(mentor);

        List<MentorReviewResponse.ReviewDetail> reviewDetails = reviews.stream()
                .map(r -> new MentorReviewResponse.ReviewDetail(
                        r.getId(),
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

    // mentor dashboard 조회
    public List<MentorDashboardItem> getMentorDashboardItems(Long mentorId) {
        memberFinder.getMemberOrThrow(mentorId);
        return documentRepository.findMentorDashboardItems(mentorId);
    }
}