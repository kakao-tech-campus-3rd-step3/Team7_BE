package com.careerfit.document.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.careerfit.document.domain.DocumentType;
import com.careerfit.document.dto.PresignedUrlResponse;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public PresignedUrlResponse generatePresignedUrl(Long userId, String originalFileName,
        String fileType, DocumentType documentType) {

        String uniqueFileName = generateUniqueFileName(userId, originalFileName);

        // documentType에 따라 디렉토리를 분리합니다.
        if(documentType.equals(DocumentType.RESUME)){
            uniqueFileName="resume/"+uniqueFileName;
        }else if(documentType.equals(DocumentType.PORTFOLIO)){
            uniqueFileName="portfolio/"+uniqueFileName;
        }

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + (1000 * 60 * 5);
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest request =
            new GeneratePresignedUrlRequest(bucket, uniqueFileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        request.addRequestParameter("Content-Type", fileType);

        URL presignedUrl = s3Client.generatePresignedUrl(request);

        return new PresignedUrlResponse(presignedUrl.toString(), uniqueFileName);
    }

    // UUID를 이용해 uniqueFileName을 생성하는 메서드입니다.
    private String generateUniqueFileName(Long userId, String originalFileName) {
        String userIdString = userId.toString();
        String uuid = UUID.randomUUID().toString();

        return userIdString + "/" + uuid + "_" + originalFileName;
    }
}
