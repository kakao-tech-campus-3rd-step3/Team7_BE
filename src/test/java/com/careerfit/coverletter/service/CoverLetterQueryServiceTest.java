package com.careerfit.coverletter.service;


import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.domain.CoverLetterItem;
import com.careerfit.coverletter.dto.CoverLetterDetailResponse;
import com.careerfit.coverletter.dto.CoverLetterListResponse;
import com.careerfit.coverletter.repository.CoverLetterJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CoverLetterQueryServiceTest {

    @InjectMocks
    private CoverLetterQueryService coverLetterQueryService;

    @Mock
    private CoverLetterFinder coverLetterFinder;

    @Mock
    private CoverLetterJpaRepository coverLetterJpaRepository;

    @Test
    @DisplayName("자기소개서 상세 조회에 성공한다")
    void getCoverLetterDetail() {
        // given
        Long documentId = 1L;
        String title = "네이버 개발자 자기소개서";
        List<CoverLetterItem> items = List.of(
            CoverLetterItem.of("성장 과정", "항상 배우는 자세로...", 1000),
            CoverLetterItem.of("지원 동기", "개발을 사랑하여...", 1000)
        );
        CoverLetter mockCoverLetter = CoverLetter.createCoverLetter(title, items);

        given(coverLetterFinder.findCoverLetter(documentId)).willReturn(mockCoverLetter);

        // when
        CoverLetterDetailResponse response = coverLetterQueryService.getCoverLetterDetail(documentId);

        // then
        assertAll(
            () -> assertThat(response.title()).isEqualTo(title),
            () -> assertThat(response.coverLetterItems()).hasSize(2),
            () -> assertThat(response.coverLetterItems().getFirst().question()).isEqualTo("성장 과정")
        );
        verify(coverLetterFinder).findCoverLetter(documentId);
    }

    @Test
    @DisplayName("특정 지원서에 속한 자기소개서 목록 조회에 성공한다")
    void getCoverLetterList() {
        // given
        Long applicationId = 1L;
        CoverLetter coverLetter1 = CoverLetter.createCoverLetter("자기소개서 1", List.of(CoverLetterItem.of("질문1", "답변1", 500)));
        CoverLetter coverLetter2 = CoverLetter.createCoverLetter("자기소개서 2", List.of(CoverLetterItem.of("질문2", "답변2", 500)));
        List<CoverLetter> mockCoverLetters = List.of(coverLetter1, coverLetter2);

        given(coverLetterJpaRepository.findAllByApplicationId(applicationId)).willReturn(mockCoverLetters);

        // when
        CoverLetterListResponse response = coverLetterQueryService.getCoverLetterList(applicationId);

        // then
        assertAll(
            () -> assertThat(response.coverLetters()).hasSize(2),
            () -> assertThat(response.coverLetters().getFirst().title()).isEqualTo("자기소개서 1")
        );
        verify(coverLetterJpaRepository).findAllByApplicationId(applicationId);
    }
}