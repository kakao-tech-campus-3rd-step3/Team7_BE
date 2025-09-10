package com.careerfit.document.service;

import com.careerfit.document.domain.Portfolio;
import com.careerfit.document.exception.PortfolioErrorCode;
import com.careerfit.document.repository.PortfolioRepository;
import com.careerfit.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioFinder {
    private final PortfolioRepository portfolioRepository;

    public Portfolio findPortfolioOrThrow(Long portfolioId){
        return portfolioRepository.findById(portfolioId)
            .orElseThrow(()->new ApplicationException(PortfolioErrorCode.PORTFOLIO_NOT_FOUND));
    }
}
