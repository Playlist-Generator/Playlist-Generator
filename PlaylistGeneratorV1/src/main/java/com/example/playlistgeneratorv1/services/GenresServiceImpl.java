package com.example.playlistgeneratorv1.services;
import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.repositories.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenresServiceImpl implements GenresService {

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


}
