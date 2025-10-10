package com.careerfit.coverletter.service;

import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.dto.CoverLetterDetailResponse;
import com.careerfit.coverletter.dto.CoverLetterListResponse;
import com.careerfit.coverletter.repository.CoverLetterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoverLetterQueryService {

    private final CoverLetterFinder coverLetterFinder;
    private final CoverLetterJpaRepository coverLetterJpaRepository;

    public CoverLetterDetailResponse getCoverLetterDetail(Long documentId) {
        CoverLetter coverLetter = coverLetterFinder.findCoverLetter(documentId);
        return CoverLetterDetailResponse.of(coverLetter.getTitle(), coverLetter.getCoverLetterItems());
    }

    public CoverLetterListResponse getCoverLetterList(Long applicationId) {
        List<CoverLetter> coverLetters = coverLetterJpaRepository.findAllByApplicationId(
            applicationId);
        return CoverLetterListResponse.of(coverLetters);
    }
}
