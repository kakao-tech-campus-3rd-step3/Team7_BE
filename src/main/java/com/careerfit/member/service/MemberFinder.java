package com.careerfit.member.service;

import com.careerfit.auth.domain.OAuthProvider;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.domain.MemberRole;
import com.careerfit.member.dto.mentor.MentorListResponse;
import com.careerfit.member.exception.MemberErrorCode;
import com.careerfit.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFinder {

    private final MemberJpaRepository memberJpaRepository;

    public Member getMemberOrThrow(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new ApplicationException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Member getMentorOrThrow(Long memberId) {
        return memberJpaRepository.findByIdWithMentorProfile(memberId)
                .filter(Member::isMentor)
                .orElseThrow(() -> new ApplicationException(MemberErrorCode.MENTOR_NOT_FOUND));
    }

    public Member getMenteeOrThrow(Long memberId) {
        return memberJpaRepository.findByIdWithMenteeProfile(memberId)
                .filter(Member::isMentee)
                .orElseThrow(() -> new ApplicationException(MemberErrorCode.MENTEE_NOT_FOUND));
    }

    public Optional<Member> getMemberWithOptional(String registrationId, String oauthId) {
        return memberJpaRepository.findByProviderAndOauthId(OAuthProvider.from(registrationId), oauthId);
    }

    public Optional<Member> getMemberWithOptional(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    public Page<MentorListResponse> getMentorPage(String search, Pageable pageable) {
        boolean hasSearch = search != null && !search.isBlank();

        Sort sort = pageable.getSort().isSorted() ? pageable.getSort() : Sort.by(Sort.Direction.DESC, "averageRating");
        Pageable unsortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        String nativeSortBy = extractNativeSortProperty(sort);
        String sortOrder = extractSortOrder(sort);

        if (!hasSearch) {
            return memberJpaRepository.findTopMentors(MemberRole.MENTOR.name(), nativeSortBy, sortOrder, unsortedPageable);
        }

        return memberJpaRepository.searchMentorsByRoleAndKeyword(
                MemberRole.MENTOR.name(),
                search + "*",
                nativeSortBy,
                sortOrder,
                unsortedPageable
        );
    }

    private String extractNativeSortProperty(Sort sort) {
        Sort.Order order = sort.stream().findFirst()
                .orElse(new Sort.Order(Sort.Direction.DESC, "averageRating"));
        return switch (order.getProperty().toLowerCase()) {
            case "rating", "averagerating", "mentorprofile.averagerating" -> "averageRating";
            case "career", "careeryears", "mentorprofile.careeryears" -> "careerYears";
            case "reviewcount", "mentorprofile.reviewcount" -> "reviewCount";
            case "menteecount", "mentorprofile.menteecount" -> "menteeCount";
            case "name" -> "name";
            default -> "averageRating";
        };
    }

    private String extractSortOrder(Sort sort) {
        Sort.Order order = sort.stream().findFirst()
                .orElse(new Sort.Order(Sort.Direction.DESC, "averageRating"));
        return order.getDirection().name();
    }
}
