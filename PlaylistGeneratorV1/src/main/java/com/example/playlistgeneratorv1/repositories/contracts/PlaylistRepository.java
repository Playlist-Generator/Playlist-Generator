package com.example.playlistgeneratorv1.repositories.contracts;


import com.example.playlistgeneratorv1.models.PlaylistFilterOptions;

import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.User;

import java.util.List;


public interface PlaylistRepository {
    List<Playlists> get(PlaylistFilterOptions playlistFilterOptions);
    Playlists getByPlaylistId(int id);
    List<Playlists> getUsersPlaylists(User user);
    List<Playlists> getHighestRank();
    void create(Playlists playlist);
    void update(Playlists playlist);
    List<Playlists> getAll();
    void delete(int playlistId);
    List<Playlists> getAllCount();
}
