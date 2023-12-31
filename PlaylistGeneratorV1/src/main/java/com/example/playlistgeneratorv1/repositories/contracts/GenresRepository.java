package com.example.playlistgeneratorv1.repositories.contracts;

import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.models.Playlists;

import java.util.List;
import java.util.Optional;

public interface GenresRepository {

    List<Genres> getAllGenres();

    Genres get(int id);

    Genres get(long id);

    Genres findByName(String name);

    void save(Genres genre);

    void create(Genres genre);
}
