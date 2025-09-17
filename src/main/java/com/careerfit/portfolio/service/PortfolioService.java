package com.careerfit.portfolio.service;

import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.portfolio.domain.Portfolio;
import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.FileCreateResponse;
import com.careerfit.document.dto.FileInfoResponse;
import com.careerfit.portfolio.exception.PortfolioErrorCode;
import com.careerfit.portfolio.repository.PortfolioRepository;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.global.util.DocumentUtil;
import com.careerfit.portfolio.service.PortfolioFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioFinder portfolioFinder;
    private final PortfolioRepository portfolioRepository;
    private final ApplicationFinder applicationFinder;

    @Transactional
    public FileInfoResponse getPortfolioInfo(Long applicationId, Long portfolioId) {
        Portfolio portfolio = portfolioFinder.findPortfolioOrThrow(portfolioId);
        verifyApplicationOwnership(applicationId, portfolio);

        return FileInfoResponse.fromPortfolio(portfolio);
    }

    public void deletePortfolio(Long applicationId, Long portfolioId) {
        Portfolio portfolio = portfolioFinder.findPortfolioOrThrow(portfolioId);
        verifyApplicationOwnership(applicationId, portfolio);

        portfolioRepository.deleteById(portfolioId);
    }

    // Portfolio 저장
    public FileCreateResponse completeUpload(Long requestApplicationId,
        CompleteUploadRequest request) {

        Long applicationId = DocumentUtil.extractApplicationId(request.uniqueFileName());
        String documentTitle = DocumentUtil.extractDocumentTitle(request.uniqueFileName());
        String originalFileName = DocumentUtil.extractOriginalFileName(request.uniqueFileName());

        // /api/application/{requestApplicationId}/~와 presignedUrl에 담긴 applicationId가 다르면 예외 발생
        if (!applicationId.equals(requestApplicationId)) {
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_UNMATCHED);
        }

        Portfolio portfolio = Portfolio.of(originalFileName, request.uniqueFileName(),
            documentTitle, applicationFinder.getApplicationOrThrow(applicationId));

        portfolioRepository.save(portfolio);

        return FileCreateResponse.fromPortfolio(portfolio);
    }

    private void verifyApplicationOwnership(Long applicationId, Portfolio portfolio) {
        if (!portfolio.getApplication().getId().equals(applicationId)) {
            throw new ApplicationException(PortfolioErrorCode.PORTFOLIO_NOT_MATCHED)
                .addErrorInfo("request application Id", applicationId);
        }
    }
}
