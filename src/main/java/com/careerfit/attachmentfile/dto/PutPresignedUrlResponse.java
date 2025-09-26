package com.careerfit.attachmentfile.dto;

import java.time.LocalDateTime;

public record PutPresignedUrlResponse(
    String presignedUrl,
    String uniqueFileName,
    LocalDateTime expiredAt)
{
    public static PutPresignedUrlResponse of(String presignedUrl, String uniqueFileName, LocalDateTime expiredAt){
        return new PutPresignedUrlResponse(presignedUrl, uniqueFileName, expiredAt);
    }
}
