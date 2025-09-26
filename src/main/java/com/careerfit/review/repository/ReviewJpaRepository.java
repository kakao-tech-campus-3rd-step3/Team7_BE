package com.careerfit.review.repository;

import com.careerfit.member.domain.Member;
import com.careerfit.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    List<Review> findByMentor(Member mentor);
}
