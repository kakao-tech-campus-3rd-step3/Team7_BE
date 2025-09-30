package com.careerfit.attachmentfile.service;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.dto.FileUploadRequest;
import com.careerfit.attachmentfile.dto.PutPresignedUrlResponse;
import com.careerfit.document.domain.DocumentType;
import com.careerfit.global.config.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

import static com.careerfit.global.util.DocumentUtil.*;

@Service
@RequiredArgsConstructor
@Transactional
public class S3CommandService {

    private final AwsProperties awsProperties;
    private final S3Presigner s3Presigner;
    private final S3Client s3Client;
    private final AttachmentFileFinder attachmentFileFinder;


    // 파일 업로드
    public PutPresignedUrlResponse generatePutPresignedUrl(
        Long applicationId,
        DocumentType documentType,
        FileUploadRequest fileUploadRequest
    ) {
        String uniqueFileName = generateUniqueFileName(applicationId, documentType,
            fileUploadRequest.documentTitle(), fileUploadRequest.fileName());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(awsProperties.s3().bucket())
            .key(uniqueFileName)
            .contentType(fileUploadRequest.fileType())
            .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(putObjectRequest)
            .build();

        String presignedUrl = s3Presigner.presignPutObject(putObjectPresignRequest)
            .url().toString();

        return new PutPresignedUrlResponse(presignedUrl, uniqueFileName);
    }

    // 파일 삭제
    public void deleteFile(
        Long applicationId,
        Long attachmentFileId
    ) {
        AttachmentFile attachmentFile = attachmentFileFinder.findAttachmentFileOrThrow(
            attachmentFileId);
        AttachmentFileVerifier.verifyApplicationOwnership(applicationId, attachmentFile);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(awsProperties.s3().bucket())
            .key(attachmentFile.getStoredFilePath())
            .build();
        s3Client.deleteObject(deleteObjectRequest);
    }


    // UUID를 이용해 uniqueFileName을 생성하는 메서드입니다.
    // applications/{applicationId}/attachment-files/uuid_documentTitle_originalFileName
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
