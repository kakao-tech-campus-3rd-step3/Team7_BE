package com.careerfit.document.service;

import static java.lang.Long.parseLong;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.document.domain.Resume;
import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.PresignedUrlRequest;
import com.careerfit.document.dto.PresignedUrlResponse;
import com.careerfit.document.dto.ResumeCreateResponse;
import com.careerfit.document.repository.ResumeRepository;
import com.careerfit.global.exception.ApplicationException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ApplicationFinder applicationFinder;

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // presignedUrl 생성
    public PresignedUrlResponse generatePresignedUrl(Long applicationId,
        PresignedUrlRequest presignedUrlRequest) {
        String uniqueFileName = generateUniqueFileName(applicationId,
            presignedUrlRequest.documentTitle(), presignedUrlRequest.fileName());

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + (1000 * 60 * 5);
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest request =
            new GeneratePresignedUrlRequest(bucket, uniqueFileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        request.addRequestParameter("Content-Type", presignedUrlRequest.fileType());

        URL presignedUrl = s3Client.generatePresignedUrl(request);

        // uniqueFileName에는 아래 정보가 들어갑니다.
        // application/{applicationId}/resume/uuid_documentTitle_originalFileName
        return new PresignedUrlResponse(presignedUrl.toString(), uniqueFileName);
    }

    // 파일 업로드 완료 처리 + entity 저장
    // CompleteUploadRequest에는 presignedUrl 발급 시에 전달받은 UniqueFileName 필드만 존재
    public ResumeCreateResponse completeUploadFile(Long requestApplicationId, CompleteUploadRequest request) {
        String filePath = request.uniqueFileName();

        // application/{applicationId}/resume/uuid_documentTitle_originalFileName
        String[] parts = filePath.split("/");
        Long applicationId = parseLong(parts[1]);

        // /api/application/{requestApplicationId}/~와 presignedUrl에 담긴 applicationId가 다르면 예외 발생
        if(!applicationId.equals(requestApplicationId)){
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_UNMATCHED);
        }

        String[] nameParts = parts[3].split("_", 3);
        String documentTitle = nameParts[1];
        String originalFileName = nameParts[2];

        Resume resume = Resume.builder()
            .originalFileName(originalFileName)
            .storedFilePath(request.uniqueFileName())
            .title(documentTitle)
            .application(applicationFinder.getApplicationOrThrow(applicationId))
            .build();

        resumeRepository.save(resume);

        return ResumeCreateResponse.from(resume);
    }

    // UUID를 이용해 uniqueFileName을 생성하는 메서드입니다.
    // application/{applicationId}/resume/uuid_documentTitle_originalFileName
    private String generateUniqueFileName(Long applicationId, String documentTitle,
        String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        return "applications/" + applicationId + "/" + "resume/" + uuid + "_" + documentTitle + "_"
            + originalFileName;
    }

}
