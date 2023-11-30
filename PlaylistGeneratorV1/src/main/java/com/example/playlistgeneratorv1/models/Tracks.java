package com.example.playlistgeneratorv1.models;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Tracks")
public class Tracks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private Albums album;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genres genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id")
    private Artists artist;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "link", length = 255)
    private String link;

    @Column(name = "artist_name", length = 255)
    private String artistName;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "ranks")
    private int ranks;

    @Column(name = "preview_url", length = 255)
    private String previewUrl;
   public Tracks(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Albums getAlbum() {
        return album;
    }

    public void setAlbum(Albums album) {
        this.album = album;
    }

    public Genres getGenre() {
        return genre;
    }

    public void setGenre(Genres genre) {
        this.genre = genre;
    }

    public Artists getArtist() {
        return artist;
    }

    public void setArtist(Artists artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRanks() {
        return ranks;
    }

    public void setRanks(Integer ranks) {
        this.ranks = ranks;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tracks track = (Tracks) o;
        return id == track.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
