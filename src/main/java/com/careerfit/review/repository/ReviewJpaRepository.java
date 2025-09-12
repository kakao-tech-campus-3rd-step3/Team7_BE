package com.careerfit.review.repository;

import com.careerfit.member.domain.Member;
import com.careerfit.review.domain.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    List<Review> findByMento(Member mento);
}
