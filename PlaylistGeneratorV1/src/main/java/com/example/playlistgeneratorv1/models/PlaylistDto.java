package com.example.playlistgeneratorv1.models;

import java.util.List;

public class PlaylistDto {
    private Integer id;
    private String title;
    private String tags;
    private Integer totalPlaytime;
    private Integer averageRank;
    private List<Tracks> tracks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getTotalPlaytime() {
        return totalPlaytime;
    }

    public void setTotalPlaytime(Integer totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    public Integer getAverageRank() {
        return averageRank;
    }

    public void setAverageRank(Integer averageRank) {
        this.averageRank = averageRank;
    }

    public List<Tracks> getTracks() {
        return tracks;
    }

    public void setTracks(List<Tracks> tracks) {
        this.tracks = tracks;
    }

    public PlaylistDto(Integer id, String title, String tags, Integer totalPlaytime, Integer averageRank) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.totalPlaytime = totalPlaytime;
        this.averageRank = averageRank;
    }
}
