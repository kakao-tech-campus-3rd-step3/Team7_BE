package com.careerfit.document.service;

import com.careerfit.document.domain.Portfolio;
import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.document.exception.FileException;
import com.careerfit.document.exception.PortfolioErrorCode;
import com.careerfit.document.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioFinder portfolioFinder;
    private final PortfolioRepository portfolioRepository;

    public FileInfoResponse getPortfolioInfo(Long applicationId, Long portfolioId) {
        Portfolio portfolio = portfolioFinder.findPortfolioOrThrow(portfolioId);

        if (!portfolio.getApplication().getId().equals(applicationId)) {
            throw new FileException(PortfolioErrorCode.PORTFOLIO_NOT_MATCHED);
        }

        return FileInfoResponse.fromPortfolio(portfolio);
    }

    public void deletePortfolio(Long applicationId, Long portfolioId){
        Portfolio portfolio = portfolioFinder.findPortfolioOrThrow(portfolioId);

        if (!portfolio.getApplication().getId().equals(applicationId)) {
            throw new FileException(PortfolioErrorCode.PORTFOLIO_NOT_MATCHED);
        }

        portfolioRepository.deleteById(portfolioId);
    }
}
