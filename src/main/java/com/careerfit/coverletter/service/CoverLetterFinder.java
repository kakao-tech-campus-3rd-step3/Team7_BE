package com.careerfit.coverletter.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.exception.CoverLetterErrorCode;
import com.careerfit.coverletter.repository.CoverLetterJpaRepository;
import com.careerfit.global.exception.ApplicationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoverLetterFinder {

    private final CoverLetterJpaRepository coverLetterJpaRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CoverLetter findCoverLetter(Long documentId){
        return coverLetterJpaRepository.findById(documentId)
            .orElseThrow(()-> new ApplicationException(CoverLetterErrorCode.COVER_LETTER_NOT_FOUND));
    }
}
