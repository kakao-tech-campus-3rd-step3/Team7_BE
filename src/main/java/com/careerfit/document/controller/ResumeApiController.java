package com.careerfit.document.controller;

import com.careerfit.document.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications/{application-id}")
public class ResumeApiController {

    private final ResumeService resumeService;


}