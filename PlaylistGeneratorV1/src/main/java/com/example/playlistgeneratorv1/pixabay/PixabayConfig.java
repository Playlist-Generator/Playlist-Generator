package com.example.playlistgeneratorv1.pixabay;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PixabayConfig {
    @Value("${pixabay.api.base-url}")
    private String baseUrl;

    @Value("${pixabay.api.key}")
    private String apiKey;
}
