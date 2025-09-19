package com.careerfit.member.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberRole;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndOauthId(OAuthProvider oAuthProvider, String oauthId);

    Optional<Member> findByEmail(String email);

    @Query(value = """
            SELECT m
            FROM Member m
            LEFT JOIN FETCH m.mentorProfile mp
            WHERE m.memberRole = :role
              AND (
                LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(mp.company) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(mp.jobPosition) LIKE LOWER(CONCAT('%', :search, '%'))
              )
        """,
        countQuery = """
                SELECT COUNT(m)
                FROM Member m
                JOIN m.mentorProfile mp
                WHERE m.memberRole = :role
                  AND (
                    LOWER(m.name) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(mp.company) LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(mp.jobPosition) LIKE LOWER(CONCAT('%', :search, '%'))
                  )
            """)
    Page<Member> findByRoleAndSearch(
        @Param("role") MemberRole role,
        @Param("search") String search,
        Pageable pageable
    );

    @Query("SELECT m FROM Member m " +
        "LEFT JOIN FETCH m.menteeProfile mp " +
        "WHERE m.id = :id AND m.memberRole = 'MENTEE'")
    Optional<Member> findMenteeByIdWithDetails(@Param("id") Long id);

    @Query("SELECT m FROM Member m " +
        "LEFT JOIN FETCH m.mentorProfile mp " +
        "WHERE m.id = :id AND m.memberRole = 'MENTO'")
    Optional<Member> findMentorByIdWithDetails(@Param("id") Long id);
}
