package com.careerfit.portfolio.repository;

import com.careerfit.portfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
