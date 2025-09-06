package com.careerfit.coverletter.service;

import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.exception.CoverLetterErrorCode;
import com.careerfit.coverletter.repository.CoverLetterJpaRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CoverLetterFinder {

    private final CoverLetterJpaRepository coverLetterJpaRepository;

    public CoverLetter findCoverLetter(Long documentId) {
        return coverLetterJpaRepository.findById(documentId)
                .orElseThrow(() -> new ApplicationException(CoverLetterErrorCode.COVER_LETTER_NOT_FOUND));
    }
}
