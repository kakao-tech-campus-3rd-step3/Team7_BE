package com.careerfit.document.dto;

import com.careerfit.document.domain.Portfolio;

public record PortfolioCreateResponse(
    Long id,
    String originalFileName,
    String title
) {
    public static PortfolioCreateResponse from(Portfolio portfolio) {
        return new PortfolioCreateResponse(portfolio.getId(), portfolio.getOriginalFileName(),
            portfolio.getTitle());
    }
}
