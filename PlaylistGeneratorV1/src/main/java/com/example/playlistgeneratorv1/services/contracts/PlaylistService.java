package com.example.playlistgeneratorv1.services.contracts;

import com.example.playlistgeneratorv1.models.PlaylistFilterOptions;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;

import java.util.List;
import java.util.Map;

public interface PlaylistService {
    List<Playlists> getHighestRankPlaylist();
    List<Playlists> get(PlaylistFilterOptions playlistFilterOptions);
    List<Playlists> getUsersPlaylists(User user);
    List<Tracks> generatePlaylist(Map<String, Integer> genrePercentages, String origin, String destination);
    Playlists getByPlaylistId(int id);
    void create(String name,User user, Map<String, Integer> genrePercentages, String origin, String destination);
    void update(User user, Playlists playlist);
    void delete(User user, int playlistId);
    List<Playlists> getAll();
    int showPostsCount();
}
