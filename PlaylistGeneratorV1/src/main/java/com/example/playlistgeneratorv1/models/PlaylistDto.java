package com.example.playlistgeneratorv1.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Map;

public class PlaylistDto {
    public static final String PLAYLIST_TITLE_LENGTH_ERROR = "Title must be between 4 and 40 symbols.";
    @NotEmpty
    @Size(min = 4, max = 40, message = PLAYLIST_TITLE_LENGTH_ERROR)
    private String title;

    @NotEmpty
    private String from;

    @NotEmpty
    private String to;

    private Map<String, Double> genres;

    private boolean useTopTracks;

    private boolean tracksFromSameArtist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String, Double> getGenres() {
        return genres;
    }

    public void setGenres(Map<String, Double> genres) {
        this.genres = genres;
    }

    public boolean isUseTopTracks() {
        return useTopTracks;
    }

    public void setUseTopTracks(boolean useTopTracks) {
        this.useTopTracks = useTopTracks;
    }

    public boolean isTracksFromSameArtist() {
        return tracksFromSameArtist;
    }

    public void setTracksFromSameArtist(boolean tracksFromSameArtist) {
        this.tracksFromSameArtist = tracksFromSameArtist;
    }
}
