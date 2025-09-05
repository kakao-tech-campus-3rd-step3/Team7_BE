package com.careerfit.document.controller;

import com.careerfit.document.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{application-id}")
public class PortfolioApiController {

    private final PortfolioService portfolioService;

}