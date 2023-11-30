package com.example.playlistgeneratorv1.deezer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerGenreResponse {

    private List<DeezerGenre> data;
}
