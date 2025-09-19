package com.careerfit.application.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.dto.ApplicationDetailHeaderResponse;
import com.careerfit.application.dto.ApplicationListResponse;
import com.careerfit.application.dto.ApplicationSummaryResponse;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.global.exception.ApplicationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationQueryService {

    private final ApplicationJpaRepository applicationJpaRepository;

    public ApplicationListResponse getApplicationList(Long memberId) {
        List<ApplicationSummaryResponse> summaries = applicationJpaRepository.findAllByMemberId(
                        memberId)
                .stream()
                .map(ApplicationSummaryResponse::from)
                .collect(Collectors.toList());

        return ApplicationListResponse.from(summaries);
    }

    public ApplicationDetailHeaderResponse getApplicationDetailHeader(Long applicationId) {
        Application application = applicationJpaRepository.findById(applicationId)
                .orElseThrow(
                        () -> new ApplicationException(ApplicationErrorCode.APPLICATION_NOT_FOUND));

        return ApplicationDetailHeaderResponse.from(application);
    }
}
