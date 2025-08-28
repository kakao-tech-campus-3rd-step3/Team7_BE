package com.careerfit.auth.provider;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.careerfit.auth.dto.KakaoTokenResponse;
import com.careerfit.auth.dto.KakaoUserInfoResponse;
import com.careerfit.auth.exception.KakaoApiClientException;
import com.careerfit.auth.exception.KakaoApiServerException;
import com.careerfit.auth.property.KakaoOauthProperties;

import java.net.URI;

@Service
public class KakaoOauthProvider {

    private final RestClient restClient;
    private final KakaoOauthProperties properties;

    public KakaoOauthProvider(RestClient.Builder builder, KakaoOauthProperties properties) {
        this.restClient = builder
            .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                throw new KakaoApiClientException(response.getStatusText());
            })
            .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
                throw new KakaoApiServerException(response.getStatusText());
            })
            .build();
        this.properties = properties;
    }

    public KakaoTokenResponse getAccessToken(String code) {
        URI uri = UriComponentsBuilder
            .fromUriString(properties.urls().getTokenUrl())
            .build()
            .toUri();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.clientId());
        body.add("redirect_uri", properties.redirectUri());
        body.add("code", code);
        body.add("client_secret", properties.clientSecret());

        return restClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .body(KakaoTokenResponse.class);
    }

    public KakaoTokenResponse reissueAccessToken(String refreshToken) {
        URI uri = UriComponentsBuilder
            .fromUriString(properties.urls().getTokenUrl())
            .build()
            .toUri();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.clientId());
        body.add("refresh_token", refreshToken);
        body.add("client_secret", properties.clientSecret());

        return restClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .body(KakaoTokenResponse.class);
    }

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        URI uri = UriComponentsBuilder
            .fromUriString(properties.urls().getUserInfoUrl())
            .build()
            .toUri();

        return restClient.get()
            .uri(uri)
            .header("Authorization", "Bearer " + accessToken)
            .retrieve()
            .body(KakaoUserInfoResponse.class);
    }

}