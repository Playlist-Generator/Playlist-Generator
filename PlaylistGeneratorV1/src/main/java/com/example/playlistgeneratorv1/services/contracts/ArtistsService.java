package com.example.playlistgeneratorv1.services.contracts;

import com.example.playlistgeneratorv1.models.Artists;

import java.util.List;
import java.util.Optional;

public interface ArtistsService {


    List<Artists> getAllArtists();

    Artists getArtistById(int id);

    Artists getArtistByName(String name);

    Artists saveArtist(Artists artist);

    void updateArtist(Artists artist);

    void deleteArtist(int id);
}
