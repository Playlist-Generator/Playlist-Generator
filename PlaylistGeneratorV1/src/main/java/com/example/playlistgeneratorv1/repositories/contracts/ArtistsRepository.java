package com.example.playlistgeneratorv1.repositories.contracts;

import com.example.playlistgeneratorv1.models.Artists;

import java.util.List;

public interface ArtistsRepository {

    List<Artists> getAll();

    Artists getById(int id);

    Artists getByName(String name);

    void create(Artists artist);

    void update(Artists artist);

    void delete(int id);
}
