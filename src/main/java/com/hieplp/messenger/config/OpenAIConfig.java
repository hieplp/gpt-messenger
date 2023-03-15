package com.hieplp.messenger.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;

@Configuration
@ConfigurationProperties(prefix = "openai")
@Indexed
@Data
@Slf4j
public class OpenAIConfig {
    @Value("${openai.apiKey}")
    private String apiKey;
    private String gptModel;
    private String audioModel;
    private HTTPClient httpClient;
    private URL urls;

    @Bean
    public Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return request -> request.header("Authorization", "Bearer " + apiKey);
    }

    @Data
    public static class HTTPClient {
        private int readTimeout;
        private int connectTimeout;
    }

    @Data
    public static class URL {
        private String baseUrl;
        private String chatUrl;
        private String createTranscriptionUrl;
    }
}
