package com.careerfit.mentoring.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.repository.MemberJpaRepository;
import com.careerfit.mentoring.domain.Mentoring;
import com.careerfit.mentoring.dto.MentoringCreateRequest;
import com.careerfit.mentoring.exception.MentoringErrorCode;
import com.careerfit.mentoring.repository.MentoringJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentoringCommandService {

    private final MentoringJpaRepository mentoringJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ApplicationJpaRepository applicationJpaRepository;

    public Long createMentoring(MentoringCreateRequest dto, Long menteeId) {
        Member mentee = memberJpaRepository.findById(menteeId)
            .orElseThrow(() -> new ApplicationException(MentoringErrorCode.MENTEE_NOT_FOUND));

        Member mentor = memberJpaRepository.findById(dto.mentorId())
            .orElseThrow(() -> new ApplicationException(MentoringErrorCode.MENTOR_NOT_FOUND));

        Application application = applicationJpaRepository.findById(dto.documentId())
            .orElseThrow(() -> new ApplicationException(MentoringErrorCode.DOCUMENT_NOT_FOUND));


        Mentoring mentoring = Mentoring.of(
            application,
            mentor,
            mentee,
            dto.title(),
            dto.description(),
            dto.documentId(),
            dto.dueDate()
        );

        return mentoringJpaRepository.save(mentoring).getId();
    }

    public void deleteMentoring(Long id, Long menteeId) {
        Mentoring mentoring = mentoringJpaRepository.findById(id)
            .orElseThrow(() -> new ApplicationException(MentoringErrorCode.MENTORING_NOT_FOUND));

        if (!mentoring.getMentee().getId().equals(menteeId)) {
            throw new ApplicationException(MentoringErrorCode.UNAUTHORIZED_DELETE);
        }
        mentoringJpaRepository.delete(mentoring);
    }
}
