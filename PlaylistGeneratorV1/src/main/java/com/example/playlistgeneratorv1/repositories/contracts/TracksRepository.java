package com.example.playlistgeneratorv1.repositories.contracts;

import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.models.Tracks;

import java.util.List;
import java.util.Set;

public interface TracksRepository {
    List<Tracks> get();
    Tracks get(int id);

    Tracks get(long id);

    Tracks get(String title);
    List<Tracks> getByGenre(String genre);
    void create(Tracks track);
    void update(Tracks track);

    void delete(int id);

    double findAveragePlayTimeForGenre(String genre);

    Set<Tracks> findTrackByGenreAndDistinctArtist(String genre, int limit);

    Set<Tracks> findTopTrackByGenreAndDistinctArtist(String genre, int limit);

    Set<Tracks> findTrackByGenre(String genre, int limit);

    Set<Tracks> findTopTrackByGenre(String genre, int limit);
}
