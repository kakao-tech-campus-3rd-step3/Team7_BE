package com.careerfit.member.service.mentor;

import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.mentor.MentorProfile;
import com.careerfit.member.dto.mentor.MentorHeaderResponse;
import com.careerfit.member.dto.mentor.MentorIntroductionResponse;
import com.careerfit.member.dto.mentor.MentorListPageResponse;
import com.careerfit.member.dto.mentor.MentorReviewResponse;
import com.careerfit.member.service.MemberFinder;
import com.careerfit.review.domain.Review;
import com.careerfit.review.repository.ReviewJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class MentorQueryServiceTest {

    @Mock
    private MemberFinder memberFinder;

    @Mock
    private ReviewJpaRepository reviewRepository;

    @InjectMocks
    private MentorQueryService queryService;

    private Member mentor;
    private MentorProfile profile;
    private Member mentee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        profile = MentorProfile.of(
            10,
            "삼성전자",
            "Backend Developer",
            "재직증명서",
            null, null, null,
            "안녕하세요",
            null,
            4.5
        );

        mentor = Member.mentor(
            "mentor@test.com",
            "010-1111-1111",
            "멘토",
            null,
            null,
            "oauth123",
            profile
        );

        mentee = Member.mentee(
            "mentee@test.com",
            "010-2222-2222",
            "멘티",
            null,
            null,
            "oauth456",
            null
        );
    }

    @Test
    void getMentors_success() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mentorProfile.averageRating"));
        Page<Member> page = new PageImpl<>(List.of(mentor), pageable, 1);

        when(memberFinder.getMentorPage(nullable(String.class), any(Pageable.class)))
            .thenReturn(page);


        MentorListPageResponse result = queryService.getMentors(null, 0, 10, null, null);

        assertThat(result.pageInfo().totalElements()).isEqualTo(1);
        assertThat(result.mentors()).hasSize(1);
        assertThat(result.mentors().get(0).name()).isEqualTo("멘토");
    }

    @Test
    void getMentorHeader_success() {
        when(memberFinder.getMentorOrThrow(1L)).thenReturn(mentor);

        MentorHeaderResponse header = queryService.getMentorHeader(1L);

        assertThat(header.id()).isEqualTo(mentor.getId());
        assertThat(header.name()).isEqualTo("멘토");
        assertThat(header.company()).isEqualTo("삼성전자");
        assertThat(header.jobPosition()).isEqualTo("Backend Developer");
        assertThat(header.averageRating()).isEqualTo(4.5);
    }

    @Test
    void getMentorIntroduction_success() {
        when(memberFinder.getMentorOrThrow(1L)).thenReturn(mentor);

        MentorIntroductionResponse intro = queryService.getMentorIntroduction(1L);

        assertThat(intro.introduction()).isEqualTo("안녕하세요");
        assertThat(intro.educations()).isEmpty();
        assertThat(intro.expertises()).isEmpty();
        assertThat(intro.certifications()).isEmpty();
        assertThat(intro.careers()).isEmpty();
    }

    @Test
    void getMentorReviews_success() {
        Review review = Review.create(mentee, mentor, 5.0, "좋아요");
        when(memberFinder.getMentorOrThrow(1L)).thenReturn(mentor);
        when(reviewRepository.findByMentor(mentor)).thenReturn(List.of(review));

        MentorReviewResponse reviews = queryService.getMentorReviews(1L);

        assertThat(reviews.reviewCount()).isEqualTo(1);
        assertThat(reviews.averageRating()).isEqualTo(5.0);
        assertThat(reviews.reviews()).hasSize(1);
        assertThat(reviews.reviews().get(0).content()).isEqualTo("좋아요");
    }

}

