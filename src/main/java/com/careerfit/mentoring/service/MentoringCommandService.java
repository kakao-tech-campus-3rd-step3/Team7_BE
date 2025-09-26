package com.careerfit.mentoring.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.auth.exception.AuthErrorCode;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import com.careerfit.mentoring.domain.Mentoring;
import com.careerfit.mentoring.dto.MentoringCreateRequest;
import com.careerfit.mentoring.exception.MentoringErrorCode;
import com.careerfit.mentoring.repository.MentoringJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MentoringCommandService {

    private final MentoringJpaRepository mentoringJpaRepository;
    private final MemberFinder memberFinder;
    private final ApplicationFinder applicationFinder;

    public Long createMentoring(MentoringCreateRequest dto, Long menteeId) {
        Member mentee = memberFinder.getMenteeOrThrow(menteeId);
        Member mentor = memberFinder.getMentorOrThrow(dto.mentorId());
        Application application = applicationFinder.getApplicationOrThrow(dto.documentId());

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
            throw new ApplicationException(AuthErrorCode.ACCESS_DENIED);
        }
        mentoringJpaRepository.delete(mentoring);
    }
}
