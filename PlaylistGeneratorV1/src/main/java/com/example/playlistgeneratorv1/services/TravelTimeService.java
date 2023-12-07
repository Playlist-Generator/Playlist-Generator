package com.example.playlistgeneratorv1.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@Service
public class TravelTimeService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public Time getTravelTime(String origin, String destination) {
        String apiUrl = "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin=" + origin +
                "&destination=" + destination +
                "&key=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        return parseTravelTimeFromJson(jsonResponse);
    }

    public Time parseTravelTimeFromJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            if (root != null && root.has("routes") && root.get("routes").isArray()) {
                JsonNode routes = root.get("routes");

                if (routes.size() > 0) {
                    JsonNode route = routes.get(0);

                    if (route.has("legs") && route.get("legs").isArray()) {
                        JsonNode legs = route.get("legs");

                        if (legs.size() > 0) {
                            JsonNode leg = legs.get(0);

                            if (leg.has("duration") && leg.get("duration").has("value")) {
                                long seconds = leg.get("duration").get("value").asLong();
                                long hours = TimeUnit.SECONDS.toHours(seconds);
                                long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
                                long remainingSeconds = seconds % 60;

                                // Construct java.sql.Time using a dummy date (1970-01-01)
                                return new Time((int) hours, (int) minutes, (int) remainingSeconds);
                            }
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
