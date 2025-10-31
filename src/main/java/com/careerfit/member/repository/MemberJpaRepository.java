package com.careerfit.member.repository;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.member.domain.Member;
import com.careerfit.member.dto.mentor.MentorListResponse;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    Optional<Member> findByProviderAndOauthId(OAuthProvider oAuthProvider, String oauthId);

    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = {"menteeProfile"})
    @Query("SELECT m FROM Member m WHERE m.id = :memberId")
    Optional<Member> findByIdWithMenteeProfile(@Param("memberId") Long memberId);

    @EntityGraph(attributePaths = {"mentorProfile"})
    @Query("SELECT m FROM Member m WHERE m.id = :memberId")
    Optional<Member> findByIdWithMentorProfile(@Param("memberId") Long memberId);

    @Query(value = """
        SELECT 
           m.id AS id,
           m.name AS name,
           m.profile_image_url AS profileImageUrl,
           mp.company AS company,
           mp.job_position AS jobPosition,
           mp.career_years AS careerYears,
           mp.average_rating AS averageRating,
           mp.review_count AS reviewCount,
           mp.mentee_count AS menteeCount,
           mp.price_per_session AS pricePerSession
        FROM (
            SELECT m2.id
            FROM member m2
            WHERE m2.member_role = :role
              AND MATCH(m2.search_text) AGAINST(:search IN BOOLEAN MODE)
            LIMIT 1000
        ) matched
        INNER JOIN member m ON matched.id = m.id
        INNER JOIN mentor_profile mp ON m.id = mp.member_id
        ORDER BY
           CASE WHEN :sortBy = 'averageRating' AND :sortOrder = 'DESC' THEN mp.average_rating END DESC,
           CASE WHEN :sortBy = 'averageRating' AND :sortOrder = 'ASC' THEN mp.average_rating END ASC,
           CASE WHEN :sortBy = 'careerYears' AND :sortOrder = 'DESC' THEN mp.career_years END DESC,
           CASE WHEN :sortBy = 'careerYears' AND :sortOrder = 'ASC' THEN mp.career_years END ASC,
           CASE WHEN :sortBy = 'reviewCount' AND :sortOrder = 'DESC' THEN mp.review_count END DESC,
           CASE WHEN :sortBy = 'reviewCount' AND :sortOrder = 'ASC' THEN mp.review_count END ASC,
           CASE WHEN :sortBy = 'menteeCount' AND :sortOrder = 'DESC' THEN mp.mentee_count END DESC,
           CASE WHEN :sortBy = 'menteeCount' AND :sortOrder = 'ASC' THEN mp.mentee_count END ASC,
           CASE WHEN :sortBy = 'name' AND :sortOrder = 'ASC' THEN m.name END ASC,
           CASE WHEN :sortBy = 'name' AND :sortOrder = 'DESC' THEN m.name END DESC,
           mp.average_rating DESC
        """,
            countQuery = """
        SELECT COUNT(*) 
        FROM (
            SELECT 1
            FROM member m
            WHERE m.member_role = :role
              AND MATCH(m.search_text) AGAINST(:search IN BOOLEAN MODE)
            LIMIT 1000
        ) sub
        """,
            nativeQuery = true
    )
    @QueryHints({
            @QueryHint(name = "org.hibernate.timeout", value = "5000"),
            @QueryHint(name = "org.hibernate.readOnly", value = "true")
    })
    Page<MentorListResponse> searchMentorsByRoleAndKeyword(
            @Param("role") String role,
            @Param("search") String search,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            Pageable pageable
    );

    @Query(value = """
        SELECT 
           m.id AS id,
           m.name AS name,
           m.profile_image_url AS profileImageUrl,
           mp.company AS company,
           mp.job_position AS jobPosition,
           mp.career_years AS careerYears,
           mp.average_rating AS averageRating,
           mp.review_count AS reviewCount,
           mp.mentee_count AS menteeCount,
           mp.price_per_session AS pricePerSession
        FROM (
            SELECT m2.id
            FROM member m2
            WHERE m2.member_role = :role
            LIMIT 10000
        ) matched
        INNER JOIN member m ON matched.id = m.id
        INNER JOIN mentor_profile mp ON m.id = mp.member_id
        ORDER BY
           CASE WHEN :sortBy = 'averageRating' AND :sortOrder = 'DESC' THEN mp.average_rating END DESC,
           CASE WHEN :sortBy = 'averageRating' AND :sortOrder = 'ASC' THEN mp.average_rating END ASC,
           CASE WHEN :sortBy = 'careerYears' AND :sortOrder = 'DESC' THEN mp.career_years END DESC,
           CASE WHEN :sortBy = 'careerYears' AND :sortOrder = 'ASC' THEN mp.career_years END ASC,
           CASE WHEN :sortBy = 'reviewCount' AND :sortOrder = 'DESC' THEN mp.review_count END DESC,
           CASE WHEN :sortBy = 'reviewCount' AND :sortOrder = 'ASC' THEN mp.review_count END ASC,
           CASE WHEN :sortBy = 'menteeCount' AND :sortOrder = 'DESC' THEN mp.mentee_count END DESC,
           CASE WHEN :sortBy = 'menteeCount' AND :sortOrder = 'ASC' THEN mp.mentee_count END ASC,
           CASE WHEN :sortBy = 'name' AND :sortOrder = 'ASC' THEN m.name END ASC,
           CASE WHEN :sortBy = 'name' AND :sortOrder = 'DESC' THEN m.name END DESC,
           mp.average_rating DESC
        """,
            countQuery = """
        SELECT COUNT(*) 
        FROM (
            SELECT 1
            FROM member m
            WHERE m.member_role = :role
            LIMIT 10000
        ) sub
        """,
            nativeQuery = true
    )
    @QueryHints({
            @QueryHint(name = "org.hibernate.readOnly", value = "true")
    })
    Page<MentorListResponse> findTopMentors(
            @Param("role") String role,
            @Param("sortBy") String sortBy,
            @Param("sortOrder") String sortOrder,
            Pageable pageable
    );
}