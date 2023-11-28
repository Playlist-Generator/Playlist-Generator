package com.example.playlistgeneratorv1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "albums")
public class Albums {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "tracklist_url", length = 255)
    private String trackList;

    public Albums() {
    }

    public String getTrackList() {
        return trackList;
    }

    public void setTrackList(String trackList) {
        this.trackList = trackList;
    }
}
