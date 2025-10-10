package com.careerfit.global.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "cloud.aws")
@Validated
public record AwsProperties(
    @Valid
    Credentials credentials,
    @Valid
    S3 s3,
    @Valid
    Region region
) {

    public record Credentials(
        @NotBlank(message = "AWS accessKey가 비어있습니다.")
        String accessKey,

        @NotBlank(message = "AWS secretKey가 비어있습니다.")
        String secretKey
    ) {

    }

    public record S3(
        @NotBlank(message = "AWS bucket이 비어있습니다.")
        String bucket,

        @NotNull(message = "expiryTime이 비어있습니다.")
        Long expiryTime
    ) {

    }

    public record Region(
        @NotBlank(message = "AWS region이 비어있습니다.")
        String regionValue
    ) {

    }
}