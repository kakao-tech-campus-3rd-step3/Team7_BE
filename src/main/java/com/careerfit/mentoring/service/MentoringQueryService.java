package com.careerfit.mentoring.service;

import com.careerfit.global.exception.ApplicationException;
import com.careerfit.mentoring.domain.Mentoring;
import com.careerfit.mentoring.dto.MentoringDetailResponse;
import com.careerfit.mentoring.exception.MentoringErrorCode;
import com.careerfit.mentoring.repository.MentoringJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentoringQueryService {

    private final MentoringJpaRepository mentoringJpaRepository;

    public MentoringDetailResponse getMentoring(Long id) {
        Mentoring mentoring = mentoringJpaRepository.findById(id)
            .orElseThrow(() -> new ApplicationException(MentoringErrorCode.MENTORING_NOT_FOUND));

        return toDetail(mentoring);
    }

    public List<MentoringDetailResponse> getMentoringByMentee(Long menteeId) {
        return mentoringJpaRepository.findByMenteeId(menteeId).stream()
            .map(this::toDetail)
            .toList();
    }

    private MentoringDetailResponse toDetail(Mentoring m) {
        return new MentoringDetailResponse(
            m.getId(),
            m.getTitle(),
            m.getDescription(),
            m.getDueDate(),
            m.getMento().getName(),
            m.getMentee().getName(),
            m.getMentoringStatus()
        );
    }
}

