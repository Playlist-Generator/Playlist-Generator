package com.example.playlistgeneratorv1.services.contracts;

import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.models.User;

import java.util.List;

public interface AlbumsService {
    List<Albums> get();

    Albums get(int id);

    Albums get(String title);

    List<Albums> getByGenre(String genre);

    void create(Albums album);

    void update(Albums album);

    void delete(int id, User user);
}
