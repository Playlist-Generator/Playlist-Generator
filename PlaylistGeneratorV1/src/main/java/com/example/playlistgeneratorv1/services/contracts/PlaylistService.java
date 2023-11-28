package com.example.playlistgeneratorv1.services.contracts;

import com.example.playlistgeneratorv1.models.PlaylistDto;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;

import java.util.List;

public interface PlaylistService {
    List<Playlists> getByUser(int userId);
    Playlists get(int id);
    void create(PlaylistDto playlistDto, User user);
    void update(int id, PlaylistDto playlistDto);
    void delete(int id);
    void addTrack(int playlistId, int trackId);
    void removeTrack(int playlistId, int trackId);
}