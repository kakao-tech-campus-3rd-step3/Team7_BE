package com.careerfit.coverletter.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoverLetterTest {

    @Test
    @DisplayName("자기소개서 문항이 비어있으면(empty) 예외가 발생한다")
    void createCoverLetter_Fail_WhenItemsIsEmpty() {
        // given
        String title = "실패하는 자기소개서";
        List<CoverLetterItem> emptyItems = List.of();

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CoverLetter.createCoverLetter(title, emptyItems);
        });

        assertThat(exception.getMessage()).isEqualTo("자기소개서의 문항이 없을 수 없습니다.");
    }

    @Test
    @DisplayName("자기소개서 문항이 null이면 예외가 발생한다")
    void createCoverLetter_Fail_WhenItemsIsNull() {
        // given
        String title = "실패하는 자기소개서";
        List<CoverLetterItem> nullItems = null;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CoverLetter.createCoverLetter(title, nullItems);
        });

        assertThat(exception.getMessage()).isEqualTo("자기소개서의 문항이 없을 수 없습니다.");
    }

}