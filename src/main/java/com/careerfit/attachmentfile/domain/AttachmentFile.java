package com.careerfit.attachmentfile.domain;

import com.careerfit.application.domain.Application;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.attachmentfile.exception.AttachmentFileErrorCode;
import com.careerfit.document.domain.Document;
import com.careerfit.global.exception.ApplicationException;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
    private AttachmentFileType attachmentFileType;

    public static AttachmentFile of(String originalFileName, String storedFilePath,
        String documentTitle, Application application, AttachmentFileType attachmentFileType) {

        validate(originalFileName, storedFilePath, documentTitle, application, attachmentFileType);

        return AttachmentFile.builder()
            .originalFileName(originalFileName)
            .storedFilePath(storedFilePath)
            .title(documentTitle)
            .application(application)
            .attachmentFileType(attachmentFileType)
            .build();
    }

    private static void validate(String originalFileName, String storedFilePath,
        String documentTitle, Application application, AttachmentFileType attachmentFileType) {

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new ApplicationException(AttachmentFileErrorCode.INVALID_ORIGINAL_FILENAME);
        }
        if (storedFilePath == null || storedFilePath.isBlank()) {
            throw new ApplicationException(AttachmentFileErrorCode.INVALID_STORED_FILE_PATH);
        }

        if(documentTitle==null || documentTitle.isBlank()){
            throw new ApplicationException(AttachmentFileErrorCode.INVALID_DOCUMENT_TITLE);
        }

        if(application==null){
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_NOT_FOUND);
        }

        if (attachmentFileType == null) {
            throw new ApplicationException(AttachmentFileErrorCode.INVALID_ATTACHMENT_FILE_TYPE);
        }
    }
}
