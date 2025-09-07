package com.careerfit.document.service;

import static com.careerfit.global.util.DocumentUtil.APPLICATION_PREFIX;
import static com.careerfit.global.util.DocumentUtil.NAME_SEPARATOR;
import static com.careerfit.global.util.DocumentUtil.PATH_SEPARATOR;
import static com.careerfit.global.util.DocumentUtil.PORTFOLIO_PREFIX;
import static com.careerfit.global.util.DocumentUtil.RESUME_PREFIX;

import com.careerfit.application.exception.ApplicationErrorCode;
import com.careerfit.application.service.ApplicationFinder;
import com.careerfit.document.config.AwsProperties;
import com.careerfit.document.domain.DocumentType;
import com.careerfit.document.domain.Portfolio;
import com.careerfit.document.domain.Resume;
import com.careerfit.document.dto.CompleteUploadRequest;
import com.careerfit.document.dto.FileCreateResponse;
import com.careerfit.document.dto.PresignedUrlRequest;
import com.careerfit.document.dto.PresignedUrlResponse;
import com.careerfit.document.repository.PortfolioRepository;
import com.careerfit.document.repository.ResumeRepository;
import com.careerfit.global.exception.ApplicationException;
import com.careerfit.global.util.DocumentUtil;
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

    private final ResumeRepository resumeRepository;
    private final PortfolioRepository portfolioRepository;
    private final ApplicationFinder applicationFinder;
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

    // 파일 업로드 완료 처리 + entity 저장
    // CompleteUploadRequest에는 presignedUrl 발급 시에 전달받은 UniqueFileName 필드만 존재
    public FileCreateResponse completeUploadFile(Long requestApplicationId,
        DocumentType documentType,
        CompleteUploadRequest request) {

        Long applicationId = DocumentUtil.extractApplicationId(request.uniqueFileName());
        String documentTitle = DocumentUtil.extractDocumentTitle(request.uniqueFileName());
        String originalFileName = DocumentUtil.extractOriginalFileName(request.uniqueFileName());

        // /api/application/{requestApplicationId}/~와 presignedUrl에 담긴 applicationId가 다르면 예외 발생
        if (!applicationId.equals(requestApplicationId)) {
            throw new ApplicationException(ApplicationErrorCode.APPLICATION_UNMATCHED);
        }

        if (documentType.equals(DocumentType.PORTFOLIO)) {
            Portfolio portfolio = Portfolio.of(originalFileName, request.uniqueFileName(),
                documentTitle, applicationFinder.getApplicationOrThrow(applicationId));

            portfolioRepository.save(portfolio);

            return FileCreateResponse.fromPortfolio(portfolio);
        } else if (documentType.equals(DocumentType.RESUME)) {
            Resume resume = Resume.of(originalFileName, request.uniqueFileName(),
                documentTitle, applicationFinder.getApplicationOrThrow(applicationId));

            resumeRepository.save(resume);

            return FileCreateResponse.fromResume(resume);
        } else {
            // 이건 나중에 documentException 만들고 천천히 고치겠습니다.
            throw new IllegalArgumentException("문서 형식이 올바르지 않습니다.");
        }
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
