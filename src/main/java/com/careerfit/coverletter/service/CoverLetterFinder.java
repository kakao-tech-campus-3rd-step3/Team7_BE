package com.careerfit.coverletter.service;

import org.springframework.stereotype.Service;

import com.careerfit.coverletter.repository.CoverLetterJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoverLetterFinder {

    private final CoverLetterJpaRepository coverLetterJpaRepository;
}
