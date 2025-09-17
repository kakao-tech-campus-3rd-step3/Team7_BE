package com.careerfit.portfolio.service;

import com.careerfit.portfolio.domain.Portfolio;
import com.careerfit.portfolio.exception.PortfolioErrorCode;
import com.careerfit.portfolio.repository.PortfolioRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PortfolioFinder {

    private final PortfolioRepository portfolioRepository;

    public Portfolio findPortfolioOrThrow(Long portfolioId) {
        return portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new ApplicationException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));
    }
}
