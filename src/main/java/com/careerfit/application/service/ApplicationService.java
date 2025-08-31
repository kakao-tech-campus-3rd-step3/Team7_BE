package com.careerfit.application.service;

import com.careerfit.application.client.AiServerClient;
import com.careerfit.application.domain.Application;
import com.careerfit.application.dto.ApplicationRegisterRequest;
import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.member.domain.Member;
import com.careerfit.member.service.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final AiServerClient aiServerClient;
    private final ApplicationJpaRepository applicationJpaRepository;
    private final MemberFinder memberFinder;

    public JobPostingAnalysisResponse analyze(JobPostingUrlRequest request) {
        return aiServerClient.analyzeUrl(request);
    }

    @Transactional
    public void registerApplication(ApplicationRegisterRequest request, Long memberId) {
        Member member = memberFinder.getMemberOrThrow(memberId);

        Application application = Application.builder()
                .companyName(request.companyName())
                .applyPosition(request.applyPosition())
                .deadLine(request.deadline())
                .location(request.location())
                .employmentType(request.employmentType())
                .careerRequirement(request.careerRequirement())
                .member(member)
                .build();

        applicationJpaRepository.save(application);
    }
}
