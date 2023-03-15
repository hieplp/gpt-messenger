package com.hieplp.messenger.config;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "facebook")
public class FacebookConfig {
    private String accessToken;
    private String verifyToken;

    @Bean
    public FacebookClient getFacebookClient() {
        return new DefaultFacebookClient(accessToken, Version.LATEST);
    }
}
