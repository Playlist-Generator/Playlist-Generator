package com.example.playlistgeneratorv1.models;

import jakarta.persistence.*;

import java.util.*;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.*;

@Entity
@Table(name="Playlists")
public class Playlists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private int id;

    @Column(name= "name")
    private  String name;

    @Column(name = "duration")
    private Time duration;

    @Column(name="rank")
    private int rank;

    @Column(name = "photoUrl")
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User createdBy;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "playlist_track",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private List<Tracks> tracks = new ArrayList<>();


    public Playlists() {
    }



    public int getId() {
        return id;
    }

    public void setId(int playlistId) {
        this.id = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

//    public Set<Track> getTracks() {
//        return tracks;
//    }
//
//    public void setTracks(Set<Track> tracks) {
//        this.tracks = tracks;
//    }


    public List<Tracks> getTracks() {
        return tracks;
    }

    public void setTracks(List<Tracks> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Playlists playlist = (Playlists) obj;
        return id == playlist.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

