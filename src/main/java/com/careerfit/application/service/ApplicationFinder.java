package com.careerfit.application.service;

import com.careerfit.application.domain.Application;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.repository.ApplicationJpaRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationFinder {

    private final ApplicationJpaRepository applicationJpaRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Application getApplicationOrThrow(Long applicationId) {
        return applicationJpaRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.APPLICATION_NOT_FOUND));
    }

}
