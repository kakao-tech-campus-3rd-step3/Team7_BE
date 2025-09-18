package com.careerfit.coverletter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.careerfit.application.domain.Application;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.coverletter.domain.CoverLetter;
import com.careerfit.coverletter.dto.CoverLetterItemInfoRequest;
import com.careerfit.coverletter.dto.CoverLetterRegisterRequest;
import com.careerfit.coverletter.repository.CoverLetterJpaRepository;

@ExtendWith(MockitoExtension.class)
class CoverLetterCommandServiceTest {

    @InjectMocks
    private CoverLetterCommandService coverLetterCommandService;

    @Mock
    private CoverLetterJpaRepository coverLetterJpaRepository;

    @Mock
    private ApplicationFinder applicationFinder;

    @Mock
    private Application mockApplication;

    @Test
    @DisplayName("새로운 자기소개서 등록에 성공한다")
    void registerCoverLetter() {
        // given
        Long applicationId = 1L;
        CoverLetterRegisterRequest request = new CoverLetterRegisterRequest(
            "새로운 자기소개서",
            List.of(new CoverLetterItemInfoRequest("질문1", "답변1", 500))
        );
        given(applicationFinder.getApplicationOrThrow(applicationId)).willReturn(mockApplication);

        // when
        coverLetterCommandService.registerCoverLetter(applicationId, request);

        // then
        ArgumentCaptor<CoverLetter> captor = ArgumentCaptor.forClass(CoverLetter.class);
        verify(mockApplication).addDocument(captor.capture());
        CoverLetter savedCoverLetter = captor.getValue();

        assertAll(
            () -> assertThat(savedCoverLetter.getTitle()).isEqualTo("새로운 자기소개서"),
            () -> assertThat(savedCoverLetter.getCoverLetterItems()).hasSize(1),
            () -> assertThat(savedCoverLetter.getCoverLetterItems().getFirst().getQuestion()).isEqualTo("질문1")
        );
    }

    @Test
    @DisplayName("자기소개서 삭제에 성공한다")
    void deleteCoverLetter() {
        // given
        Long documentId = 1L;

        // when
        coverLetterCommandService.deleteCoverLetter(documentId);

        // then
        verify(coverLetterJpaRepository).deleteById(documentId);
    }
}