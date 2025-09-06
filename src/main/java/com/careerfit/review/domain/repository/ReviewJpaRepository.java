package com.careerfit.review.domain.repository;

import com.careerfit.member.domain.Member;
import com.careerfit.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    List<Review> findByMento(Member mento);
}
