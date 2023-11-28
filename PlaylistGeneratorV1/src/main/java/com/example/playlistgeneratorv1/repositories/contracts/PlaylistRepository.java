package com.example.playlistgeneratorv1.repositories.contracts;
import com.example.playlistgeneratorv1.models.Playlists;
import java.util.List;

    public interface PlaylistRepository {
        List<Playlists> getByUser(int userId);
        Playlists get(int id);
        void create(Playlists playlist);
        void update(Playlists playlist);
        void delete(int id);
        void addTrack(int playlistId, int trackId);
        void removeTrack(int playlistId, int trackId);
    }

