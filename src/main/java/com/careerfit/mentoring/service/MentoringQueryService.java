package com.careerfit.mentoring.service;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.mentoring.domain.Mentoring;
import com.careerfit.mentoring.dto.MentoringDetailResponse;
import com.careerfit.mentoring.exception.MentoringErrorCode;
import com.careerfit.mentoring.repository.MentoringJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoringQueryService {

    private final MentoringJpaRepository mentoringJpaRepository;

    public MentoringDetailResponse getMentoring(Long id) {
        Mentoring mentoring = mentoringJpaRepository.findById(id)
            .orElseThrow(() -> new ApplicationException(MentoringErrorCode.MENTORING_NOT_FOUND));
        return MentoringDetailResponse.from(mentoring);
    }

    public List<MentoringDetailResponse> getMentoringByMentee(Long menteeId) {
        return mentoringJpaRepository.findByMenteeId(menteeId).stream()
            .map(MentoringDetailResponse::from)
            .toList();
    }

}

