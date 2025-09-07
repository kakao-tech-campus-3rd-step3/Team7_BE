package com.careerfit.document.domain;

import com.careerfit.application.domain.Application;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "portfolio")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class Portfolio extends Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 파일 경로가 다르다면 이름은 중복되어도 괜찮습니다. unique 옵션 제외한 건 의도한 거에요.
    @Column(nullable = false)
    private String originalFileName;

    @Column(unique = true, nullable = false)
    private String storedFilePath;

    public static Portfolio of(String originalFileName, String storedFilePath, String documentTitle,
                               Application application) {

        return Portfolio.builder()
                .originalFileName(originalFileName)
                .storedFilePath(storedFilePath)
                .title(documentTitle)
                .application(application)
                .build();
    }
}
