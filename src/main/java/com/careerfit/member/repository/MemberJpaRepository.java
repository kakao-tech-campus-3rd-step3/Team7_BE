package com.careerfit.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.member.domain.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProviderAndOauthId(OAuthProvider oAuthProvider, String oauthId);
}
