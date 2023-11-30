package com.example.playlistgeneratorv1.services.contracts;

public interface DeezerService {

    void getGenres();

    void getPlaylists(String q);

    void getTracks();
}