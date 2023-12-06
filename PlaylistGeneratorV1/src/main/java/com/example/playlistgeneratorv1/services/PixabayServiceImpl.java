package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.pixabay.PixabayConfig;
import com.example.playlistgeneratorv1.pixabay.PixabayResponse;
import com.example.playlistgeneratorv1.services.contracts.PixabayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PixabayServiceImpl implements PixabayService {
    private final RestTemplate restTemplate;

    private final PixabayConfig pixabayConfig;

    @Autowired
    public PixabayServiceImpl(RestTemplate restTemplate, PixabayConfig pixabayConfig) {
        this.restTemplate = restTemplate;
        this.pixabayConfig = pixabayConfig;
    }

    @Override
    public String getPlaylistCoverUrl(){
        String url = String.format("%s?key=%s&q=music",pixabayConfig.getBaseUrl(),pixabayConfig.getApiKey());

        PixabayResponse response = restTemplate.getForObject(url, PixabayResponse.class);
        assert response != null;
        return response.getData().get((int) (Math.random() * (19))).getUrl();
    }
}
