package com.careerfit.attachmentfile.dto;

import java.time.LocalDateTime;

public record GetPresignedUrlResponse (
    String presignedUrl,
    LocalDateTime expiredAt
){
    public static GetPresignedUrlResponse of(String presignedUrl, LocalDateTime expiredAt){
        return new GetPresignedUrlResponse(presignedUrl, expiredAt);
    }
}