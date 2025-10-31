package com.careerfit.coverletter.service;

import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.dto.CoverLetterDetailResponse;
import com.careerfit.coverletter.dto.CoverLetterInfoResponse;
import com.careerfit.coverletter.repository.CoverLetterJpaRepository;
import com.careerfit.global.dto.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoverLetterQueryService {

    private final CoverLetterFinder coverLetterFinder;
    private final CoverLetterJpaRepository coverLetterJpaRepository;

    public CoverLetterDetailResponse getCoverLetterDetail(Long documentId) {
        CoverLetter coverLetter = coverLetterFinder.findCoverLetter(documentId);
        return CoverLetterDetailResponse.of(coverLetter.getTitle(),
            coverLetter.getCoverLetterItems());
    }

    public PagedResponse<CoverLetterInfoResponse> getCoverLetterList(Long applicationId,
        Pageable pageable) {
        Page<CoverLetter> coverLetters = coverLetterJpaRepository.findAllByApplicationId(
            applicationId, pageable);
        return PagedResponse.of(coverLetters, CoverLetterInfoResponse::from);
    }
}
