package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Artists;

import com.example.playlistgeneratorv1.repositories.contracts.ArtistsRepository;
import com.example.playlistgeneratorv1.services.contracts.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistsServiceImpl implements ArtistsService {

    private final ArtistsRepository artistsRepository;

    @Autowired
    public ArtistsServiceImpl(ArtistsRepository artistsRepository) {
        this.artistsRepository = artistsRepository;
    }

    @Override
    public List<Artists> getAllArtists() {
        return artistsRepository.getAll();
    }

    @Override
    public Artists getArtistById(int id) {
        return artistsRepository.getById(id);
    }

    @Override
    public Artists getArtistByName(String name) {
        return artistsRepository.getByName(name);
    }

    @Override
    public Artists saveArtist(Artists artist) {
        artistsRepository.create(artist);
        return artist;
    }

    @Override
    public void updateArtist(Artists artist) {
        artistsRepository.update(artist);
    }

    @Override
    public void deleteArtist(int id) {
        artistsRepository.delete(id);
    }
}
