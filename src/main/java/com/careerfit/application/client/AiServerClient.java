package com.careerfit.application.client;

import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AiServerClient {

    private final RestClient restClient;

    public AiServerClient(RestClient.Builder builder,
            @Value("${ai.server.base-url}") String baseUrl) {
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public JobPostingAnalysisResponse analyzeUrl(JobPostingUrlRequest request) {
        return restClient.post()
                .uri("/api/ai/analyze-url")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(JobPostingAnalysisResponse.class);
    }
}
