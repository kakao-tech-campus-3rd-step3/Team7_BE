package com.careerfit.coverletter.domain;

import com.careerfit.document.domain.Document;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cover_letter")
@DiscriminatorValue("COVER_LETTER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@Getter
public class CoverLetter extends Document {

    @Builder.Default
    @OneToMany(mappedBy = "coverLetter", cascade = CascadeType.ALL)
    private List<CoverLetterItem> coverLetterItems = new ArrayList<>();

    public static CoverLetter createCoverLetter(String title, List<CoverLetterItem> items) {
        validateItemsNull(items);
        CoverLetter coverLetter = CoverLetter.builder()
                .title(title)
                .build();

        items.forEach(coverLetter::addCoverLetterItem);

        return coverLetter;
    }

    private static void validateItemsNull(List<CoverLetterItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("자기소개서의 문항이 없을 수 없습니다.");
        }
    }

    public void addCoverLetterItem(CoverLetterItem coverLetterItem) {
        this.coverLetterItems.add(coverLetterItem);
        coverLetterItem.setCoverLetter(this);
    }
}
