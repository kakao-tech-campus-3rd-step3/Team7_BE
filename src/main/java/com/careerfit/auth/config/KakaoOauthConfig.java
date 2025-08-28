package com.careerfit.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.careerfit.auth.property.KakaoOauthProperties;

@Configuration
@EnableConfigurationProperties(KakaoOauthProperties.class)
public class KakaoOauthConfig {

}
