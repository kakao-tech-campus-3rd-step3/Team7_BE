package com.careerfit.coverletter.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cover_letter_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CoverLetterItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private int answerLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_letter_id")
    private CoverLetter coverLetter;

    public static CoverLetterItem of(String question, String answer, int answerLimit) {
        return CoverLetterItem.builder()
            .question(question)
            .answer(answer)
            .answerLimit(answerLimit)
            .build();
    }

    public void setCoverLetter(CoverLetter coverLetter) {
        this.coverLetter = coverLetter;
    }
}
