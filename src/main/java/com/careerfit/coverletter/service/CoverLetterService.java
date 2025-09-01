package com.careerfit.coverletter.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.careerfit.application.domain.Application;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.coverletter.dto.CoverLetterRegisterRequest;
import com.careerfit.coverletter.repository.CoverLetterJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoverLetterService {

    private final ApplicationFinder applicationFinder;

    @Transactional
    public void registerCoverLetter(Long applicationId, CoverLetterRegisterRequest dto){
        List<CoverLetterItem> coverLetterItems = dto.coverLetterItems().stream()
            .map(item ->
                CoverLetterItem.of(item.question(), item.answer(), item.answerLimit()))
            .toList();

        Application application = applicationFinder.getApplicationOrThrow(applicationId);
        CoverLetter coverLetter = CoverLetter.createCoverLetter(dto.title(), coverLetterItems);
        application.addDocument(coverLetter);
    }


}
