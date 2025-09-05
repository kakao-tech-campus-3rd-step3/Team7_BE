package com.careerfit.document.service;

import static com.careerfit.global.util.DocumentUtil.APPLICATION_PREFIX;
import static com.careerfit.global.util.DocumentUtil.NAME_SEPARATOR;
import static com.careerfit.global.util.DocumentUtil.PATH_SEPARATOR;
import static com.careerfit.global.util.DocumentUtil.RESUME_PREFIX;
import static java.lang.Long.parseLong;

import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.document.domain.Resume;
import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.PresignedUrlRequest;
import com.careerfit.document.dto.PresignedUrlResponse;
import com.careerfit.document.dto.ResumeCreateResponse;
import com.careerfit.document.repository.ResumeRepository;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.global.util.DocumentUtil;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final ApplicationFinder applicationFinder;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // presignedUrl 생성
    public PresignedUrlResponse generatePresignedUrl(Long applicationId,
        PresignedUrlRequest presignedUrlRequest) {
        String uniqueFileName = generateUniqueFileName(applicationId,
            presignedUrlRequest.documentTitle(), presignedUrlRequest.fileName());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(uniqueFileName)
            .contentType(presignedUrlRequest.fileType())
            .build();

        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
            .putObjectRequest(putObjectRequest)
            .signatureDuration(Duration.ofMinutes(10))
            .build();

        String presignedUrl = s3Presigner.presignPutObject(putObjectPresignRequest)
            .url().toString();

        return new PresignedUrlResponse(presignedUrl, uniqueFileName);
    }

    // 파일 업로드 완료 처리 + entity 저장
    // CompleteUploadRequest에는 presignedUrl 발급 시에 전달받은 UniqueFileName 필드만 존재
    public ResumeCreateResponse completeUploadFile(Long requestApplicationId,
        CompleteUploadRequest request) {

        Long applicationId = DocumentUtil.extractApplicationId(request.uniqueFileName());
        String documentTitle = DocumentUtil.extractDocumentTitle(request.uniqueFileName());
        String originalFileName = DocumentUtil.extractOriginalFileName(request.uniqueFileName());

        // /api/application/{requestApplicationId}/~와 presignedUrl에 담긴 applicationId가 다르면 예외 발생
        if (!applicationId.equals(requestApplicationId)) {
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_UNMATCHED);
        }

        Resume resume = Resume.of(originalFileName, request.uniqueFileName(),
            documentTitle, applicationFinder.getApplicationOrThrow(applicationId));

        resumeRepository.save(resume);

        return ResumeCreateResponse.from(resume);
    }

    // UUID를 이용해 uniqueFileName을 생성하는 메서드입니다.
    // application/{applicationId}/resume/uuid_documentTitle_originalFileName
    private String generateUniqueFileName(Long applicationId, String documentTitle,
        String originalFileName) {
        String uuid = UUID.randomUUID().toString();
        return APPLICATION_PREFIX
            + PATH_SEPARATOR + applicationId
            + PATH_SEPARATOR + RESUME_PREFIX
            + PATH_SEPARATOR + uuid
            + NAME_SEPARATOR + documentTitle
            + NAME_SEPARATOR + originalFileName;
    }

}
