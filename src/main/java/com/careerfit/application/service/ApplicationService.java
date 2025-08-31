package com.careerfit.application.service;

import com.careerfit.application.client.AiServerClient;
import com.careerfit.application.domain.Application;
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

    @Transactional
    public JobPostingAnalysisResponse analyzeAndSaveApplication(JobPostingUrlRequest request,
            Long memberId) {
        // 1. AI 서버에 URL을 보내고 분석 결과를 받아옵니다.
        JobPostingAnalysisResponse analysisResponse = aiServerClient.analyzeUrl(request);

        // 2. 현재 사용자(Member) 정보를 조회합니다.
        Member member = memberFinder.getMemberOrThrow(memberId);

        // 3. 분석 결과를 Application 엔티티로 변환합니다.
        Application application = Application.builder()
                .companyName(analysisResponse.companyName())
                .appliedPosition(analysisResponse.applyPosition())
                .deadLine(analysisResponse.deadline())
                .companyLocation(analysisResponse.location())
                .employmentType(analysisResponse.employmentType())
                .experienceRequirement(analysisResponse.careerRequirement())
                .member(member)
                .build();

        // 4. 데이터베이스에 저장합니다.
        applicationJpaRepository.save(application);

        // 5. 분석 결과를 컨트롤러에 다시 반환합니다.
        return analysisResponse;
    }
}