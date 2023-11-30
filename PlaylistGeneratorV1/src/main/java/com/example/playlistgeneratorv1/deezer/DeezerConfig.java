package com.example.playlistgeneratorv1.deezer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
@Setter
public class DeezerConfig {
    @Value("${deezer.api.base-url}")
    private String baseUrl;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
