package com.careerfit.application.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.dto.ApplicationDetailHeaderResponse;
import com.careerfit.application.dto.ApplicationListResponse;
import com.careerfit.application.dto.ApplicationSummaryResponse;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.global.exception.ApplicationException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationQueryService {

    private final ApplicationJpaRepository applicationJpaRepository;

    public ApplicationListResponse getList(Long memberId, LocalDateTime lastUpdatedDate, int size) {
        Pageable pageable = PageRequest.of(0, size + 1, Sort.by("updatedDate").descending());

        List<Application> applications;

        if (lastUpdatedDate == null) {
            // 첫 페이지 조회
            applications = applicationJpaRepository.findByMemberIdOrderByUpdatedDateDesc(memberId,
                pageable);
        } else {
            // 다음 페이지 조회
            applications = applicationJpaRepository.findByMemberIdAndUpdatedDateBeforeOrderByUpdatedDateDesc(
                memberId, lastUpdatedDate, pageable);
        }

        boolean hasNext = applications.size() > size;

        List<ApplicationSummaryResponse> summaries = applications.stream()
            .limit(size)
            .map(ApplicationSummaryResponse::from)
            .toList();

        LocalDateTime nextCursor = null;
        if (hasNext) {
            nextCursor = applications.get(size - 1).getUpdatedDate();
        }

        return new ApplicationListResponse(summaries, nextCursor, hasNext);
    }

    public ApplicationDetailHeaderResponse getDetailHeader(Long applicationId) {
        Application application = applicationJpaRepository.findById(applicationId)
            .orElseThrow(
                () -> new ApplicationException(ApplicationErrorCode.APPLICATION_NOT_FOUND));

        return ApplicationDetailHeaderResponse.from(application);
    }
}
