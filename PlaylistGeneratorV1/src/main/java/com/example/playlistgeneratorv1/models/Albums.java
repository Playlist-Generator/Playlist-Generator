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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genres_id")
    private Genres genre;


    public Albums() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public String getTrackList() {
        return trackList;
    }

    public void setTrackList(String trackList) {
        this.trackList = trackList;
    }
}
