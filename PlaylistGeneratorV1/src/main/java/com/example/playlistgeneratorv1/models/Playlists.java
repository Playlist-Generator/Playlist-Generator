package com.example.playlistgeneratorv1.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Playlists")
public class Playlists {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title")
    private String title;
    @Column(name = "total_playtime")
    private Integer totalPlaytime;

    @Column(name = "average_rank")
    private long averageRank;

    @Column(name = "photoUrl")
    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "track_playlist",
            joinColumns = {@JoinColumn(name = "playlist_id")},
            inverseJoinColumns = {@JoinColumn(name = "track_id")}
    )
    private List<Tracks> tracks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "playlists_genres",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genres> genres = new HashSet<>();
    @Transient
    private int mapDuration;

//    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Tracks> tracks;
   public Playlists() {

    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setTotalPlaytime(Integer totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    public void setAverageRank(Integer averageRank) {
        this.averageRank = averageRank;
    }

    public int getPlaylistId() {
        return id;
    }

    public void setPlaylistId(int playlistId) {
        this.id = playlistId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotalPlaytime() {
        return totalPlaytime;
    }

    public void setTotalPlaytime(int totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    public long getAverageRank() {
        return averageRank;
    }

    public void setAverageRank(long averageRank) {
        this.averageRank = averageRank;
    }

    public int getMapDuration() {
        return mapDuration;
    }

    public void setMapDuration(int mapDuration) {
        this.mapDuration = mapDuration;
    }

    public List<Tracks> getTracks() {
        return tracks;
    }
    public void setTracks(Set<Tracks> tracks) {
        if (this.tracks == null || this.tracks.isEmpty()) {
            this.tracks = new ArrayList<>();
        }
        this.tracks.addAll(tracks);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTracks(List<Tracks> tracks) {
        this.tracks = tracks;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
    }
//    public void addTrack(Tracks track) {
//        if (tracks == null) {
//            tracks = new ArrayList<>();
//        }
//        tracks.add(track);
//
//
//    }
//
//    public void removeTrack(Tracks track) {
//        if (tracks != null) {
//            tracks.remove(track);
//
//
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       Playlists playlist = (Playlists) o;
        return id == playlist.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}