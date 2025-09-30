package com.careerfit.attachmentfile.domain;

import com.careerfit.application.domain.Application;
import com.careerfit.document.domain.Document;
import com.careerfit.document.domain.DocumentType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "attachment_file")
@DiscriminatorValue("ATTACHMENT_FILE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public class AttachmentFile extends Document {

    // 파일 경로가 다르다면 이름은 중복되어도 괜찮습니다. unique 옵션 제외한 건 의도한 거에요.
    @Column(nullable = false)
    private String originalFileName;

    @Column(unique = true, nullable = false)
    private String storedFilePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType attachmentFileType;

    public static AttachmentFile createResume(String originalFileName, String storedFilePath, String documentTitle,
        Application application) {

        return AttachmentFile.builder()
            .originalFileName(originalFileName)
            .storedFilePath(storedFilePath)
            .title(documentTitle)
            .application(application)
            .attachmentFileType(DocumentType.RESUME)
            .build();
    }

    public static AttachmentFile createPortfolio(String originalFileName, String storedFilePath, String documentTitle,
        Application application) {

        return AttachmentFile.builder()
            .originalFileName(originalFileName)
            .storedFilePath(storedFilePath)
            .title(documentTitle)
            .application(application)
            .attachmentFileType(DocumentType.PORTFOLIO)
            .build();
    }

}
