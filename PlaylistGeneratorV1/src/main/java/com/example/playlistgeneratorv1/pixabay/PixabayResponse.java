package com.example.playlistgeneratorv1.pixabay;

import com.example.playlistgeneratorv1.deezer.DeezerGenre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PixabayResponse {
    @JsonProperty("hits")
    private List<PixabayHits> data;
}

