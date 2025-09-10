package com.careerfit.document.service;

import static com.careerfit.global.util.DocumentUtil.APPLICATION_PREFIX;
import static com.careerfit.global.util.DocumentUtil.NAME_SEPARATOR;
import static com.careerfit.global.util.DocumentUtil.PATH_SEPARATOR;
import static com.careerfit.global.util.DocumentUtil.PORTFOLIO_PREFIX;
import static com.careerfit.global.util.DocumentUtil.RESUME_PREFIX;

import com.careerfit.document.config.AwsProperties;
import com.careerfit.document.domain.DocumentType;
import com.careerfit.document.dto.PresignedUrlRequest;
import com.careerfit.document.dto.PresignedUrlResponse;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AwsProperties awsProperties;

    private final S3Presigner s3Presigner;

    public PresignedUrlResponse generatePresignedUrl(Long applicationId, DocumentType documentType,
        PresignedUrlRequest presignedUrlRequest) {
        String uniqueFileName = generateUniqueFileName(applicationId, documentType,
            presignedUrlRequest.documentTitle(), presignedUrlRequest.fileName());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(awsProperties.s3().bucket())
            .key(uniqueFileName)
            .contentType(presignedUrlRequest.fileType())
            .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(putObjectRequest)
            .build();

        String presignedUrl = s3Presigner.presignPutObject(putObjectPresignRequest)
            .url().toString();

        return new PresignedUrlResponse(presignedUrl, uniqueFileName);
    }

    // UUID를 이용해 uniqueFileName을 생성하는 메서드입니다.
    // applications/{applicationId}/portfolios(or resumes)/uuid_documentTitle_originalFileName
    private String generateUniqueFileName(
        Long applicationId,
        DocumentType documentType,
        String documentTitle,
        String originalFileName) {

        String uuid = UUID.randomUUID().toString();
        String result = APPLICATION_PREFIX + PATH_SEPARATOR + applicationId + PATH_SEPARATOR;

        if (documentType.equals(DocumentType.PORTFOLIO)) {
            result += PORTFOLIO_PREFIX;
        } else if (documentType.equals(DocumentType.RESUME)) {
            result += RESUME_PREFIX;
        }

        return result
            + PATH_SEPARATOR + uuid
            + NAME_SEPARATOR + documentTitle
            + NAME_SEPARATOR + originalFileName;
    }
}
