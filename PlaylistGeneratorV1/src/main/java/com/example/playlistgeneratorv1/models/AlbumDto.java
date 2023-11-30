package com.example.playlistgeneratorv1.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AlbumDto {

    @NotNull(message = "Title can't be empty")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 symbols")
    private String title;

    @NotNull(message = "Tracklist can't be empty")
    @Size(min = 2, max = 255, message = "Tracklist should be between 2 and 225 symbols")
    private String trackList;

    @Positive(message = "genresId should be positive")
    private long genresId;

    public AlbumDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrackList() {
        return trackList;
    }

    public void setTrackList(String trackList) {
        this.trackList = trackList;
    }

    public long getGenresId() {
        return genresId;
    }

    public void setGenresId(long genresId) {
        this.genresId = genresId;
    }
}
