package com.example.playlistgeneratorv1.models;

import java.util.List;

public class TopTracksDTO {
    private String artistName;
    private List<Tracks> tracks;


    public TopTracksDTO(String artistName, List<Tracks> tracks) {
        this.artistName = artistName;
        this.tracks = tracks;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<Tracks> getTracks() {
        return tracks;
    }

    public void setTracks(List<Tracks> tracks) {
        this.tracks = tracks;
    }
}
