package com.example.playlistgeneratorv1.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Playlists")
public class Playlists {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "tags", length = 255)
    private String tags;

    @Column(name = "total_playtime")
    private Integer totalPlaytime;

    @Column(name = "average_rank")
    private Integer averageRank;
   public Playlists() {

    }

    public int getPlaylistId() {
        return id;
    }

    public void setPlaylistId(int playlistId) {
        this.id = playlistId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users users) {
        this.user = users;
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

    public int getTotalPlaytime() {
        return totalPlaytime;
    }

    public void setTotalPlaytime(int totalPlaytime) {
        this.totalPlaytime = totalPlaytime;
    }

    public int getAverageRank() {
        return averageRank;
    }

    public void setAverageRank(int averageRank) {
        this.averageRank = averageRank;
    }

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