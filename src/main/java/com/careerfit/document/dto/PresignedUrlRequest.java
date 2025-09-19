package com.careerfit.document.dto;

import com.careerfit.document.domain.DocumentType;
import jakarta.validation.constraints.Pattern;

public record PresignedUrlRequest(
        @Pattern(regexp = "^[^_]*$", message = "문서 제목에는 '_'를 사용할 수 없습니다.")
        String documentTitle,

        @Pattern(regexp = "^[^_]*$", message = "파일 이름에는 '_'를 사용할 수 없습니다.")
        String fileName,
        String fileType,
        DocumentType documentType
) {}
