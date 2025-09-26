package com.careerfit.attachmentfile.service;

import com.careerfit.attachmentfile.domain.AttachmentFile;
import com.careerfit.attachmentfile.dto.GetPresignedUrlResponse;
import com.careerfit.global.config.AwsProperties;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class S3QueryService {

    private final AwsProperties awsProperties;
    private final S3Presigner s3Presigner;
    private final AttachmentFileFinder attachmentFileFinder;

    public GetPresignedUrlResponse generateGetPresignedUrl(Long applicationid,
        Long attachmentFileId) {

        Duration expiryTime = Duration.ofMinutes(5);

        AttachmentFile attachmentFile = attachmentFileFinder.findAttachmentFileOrThrow(
            attachmentFileId);

        AttachmentFileVerifier.verifyApplicationOwnership(applicationid, attachmentFile);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(awsProperties.s3().bucket())
            .key(attachmentFile.getStoredFilePath())
            .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(expiryTime)
            .getObjectRequest(getObjectRequest)
            .build();

        String result = s3Presigner.presignGetObject(presignRequest).url().toString();

        return GetPresignedUrlResponse.of(result, LocalDateTime.now().plus(expiryTime));
    }

}
