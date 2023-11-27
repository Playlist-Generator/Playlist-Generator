package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.models.Playlists;

import java.util.List;

public interface GenresService {
    List<Genres> getAllGenres();
    Genres get(int id);

}
