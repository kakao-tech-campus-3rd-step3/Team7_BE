package com.careerfit.attachmentfile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FileUploadRequest(
        @Pattern(regexp = "^[^_]*$", message = "문서 제목에는 '_'를 사용할 수 없습니다.")
        @NotBlank(message = "문서명을 입력해 주세요.")
        String documentTitle,

        @Pattern(regexp = "^[^_]*$", message = "파일 이름에는 '_'를 사용할 수 없습니다.")
        @NotBlank(message = "파일명을 입력해 주세요.")
        String fileName,

        @NotBlank(message = "파일타입을 입력해 주세요.")
        String fileType
) {}
