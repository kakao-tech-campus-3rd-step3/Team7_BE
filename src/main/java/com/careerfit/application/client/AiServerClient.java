package com.careerfit.application.client;

import com.careerfit.application.dto.JobPostingAnalysisResponse;
import com.careerfit.application.dto.JobPostingUrlRequest;
import com.careerfit.application.exception.ApiClientException;
import com.careerfit.application.exception.ApiServerException;
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
                .defaultStatusHandler(status -> status.is4xxClientError(), (req, res) -> {
                    throw new ApiClientException("AI 서버 요청 시 문제가 발생했습니다: " + res.getStatusText());
                })
                .defaultStatusHandler(status -> status.is5xxServerError(), (req, res) -> {
                    throw new ApiServerException("AI 서버에 문제가 발생했습니다: " + res.getStatusText());
                })
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
