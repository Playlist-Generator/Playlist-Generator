package com.example.playlistgeneratorv1.repositories.contracts;

import com.example.playlistgeneratorv1.models.Tracks;

import java.util.List;

public interface TracksRepository {
    List<Tracks> get();
    Tracks get(int id);
    Tracks get(String title);
    List<Tracks> getByGenre(String genre);
    void create(Tracks track);
    void update(Tracks track);

    void delete(int id);
}
