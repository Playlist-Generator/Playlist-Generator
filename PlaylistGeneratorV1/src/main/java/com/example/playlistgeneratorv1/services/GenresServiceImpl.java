package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.repositories.contracts.GenresRepository;
import com.example.playlistgeneratorv1.services.contracts.GenresService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresServiceImpl implements GenresService {

    private static final String DEEZER_GENRE_API = "https://api.deezer.com/genre";
    private final GenresRepository genresRepository;

    @Autowired
    public GenresServiceImpl(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Override
    public List<Genres> getAllGenres() {
        return genresRepository.getAllGenres();
    }

    @Override
    public Genres get(int id) {
        return genresRepository.get(id);
    }

    @Override
    public Genres get(long id) {
        return  genresRepository.get(id);
    }


}
