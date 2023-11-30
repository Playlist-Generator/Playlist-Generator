package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.repositories.contracts.GenresRepository;
import com.example.playlistgeneratorv1.services.contracts.GenresService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
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
        return genresRepository.get(id);
    }

    @PostConstruct
    public void updateGenres() {
        RestTemplate restTemplate = new RestTemplate();
        Genres[] genres = restTemplate.getForObject(DEEZER_GENRE_API, Genres[].class);


        if (genres != null) {

            List<Genres> genresList = Arrays.asList(genres);

            for (Genres genre : genresList) {

                genre.setId((int) genre.getId());
                genre.setGenre(genre.getGenre());
                genresRepository.save(genre);
            }
        }
    }
}
