package com.example.playlistgeneratorv1.repositories.contracts;

import com.example.playlistgeneratorv1.models.Albums;

import java.util.List;

public interface AlbumsRepository {
    List<Albums> get();

    Albums get(long id);

    Albums get(String title);

    List<Albums> getByGenre(String genre);

    void create(Albums album);

    void update(Albums album);

    void delete(int id);
}
