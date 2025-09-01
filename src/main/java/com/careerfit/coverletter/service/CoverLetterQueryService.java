package com.careerfit.coverletter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.dto.CoverLetterDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoverLetterQueryService {

    private final CoverLetterFinder coverLetterFinder;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CoverLetterDetailResponse getCoverLetterDetail(Long documentId){
        CoverLetter coverLetter = coverLetterFinder.findCoverLetter(documentId);
        return CoverLetterDetailResponse.of(coverLetter.getTitle(), coverLetter.getCoverLetterItems());
    }
}
