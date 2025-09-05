package com.careerfit.document.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class AwsS3Config {

    private final AwsProperties awsProperties;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            awsProperties.credentials().accessKey(),
            awsProperties.credentials().secretKey());

        return S3Client.builder()
            .region(Region.of(awsProperties.region().regionValue()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            awsProperties.credentials().accessKey(),
            awsProperties.credentials().secretKey());

        return S3Presigner.builder()
            .region(Region.of(awsProperties.region().regionValue()))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();
    }

}
