package com.example.playlistgeneratorv1.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Artists")
public class Artists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "track_list")
    private String trackList;

    public Artists() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrackList() {
        return trackList;
    }

    public void setTrackList(String trackList) {
        this.trackList = trackList;
    }
}