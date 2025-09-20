package com.careerfit.attachmentfile.dto;

public record GetPresignedUrlResponse (
    String presignedUrl
){
    public static GetPresignedUrlResponse from(String presignedUrl){
        return new GetPresignedUrlResponse(presignedUrl);
    }
}
