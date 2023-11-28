package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.repositories.contracts.GenresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GenresServiceImplTest {

    @Mock
    private GenresRepository genresRepository;

    @InjectMocks
    private GenresServiceImpl genresService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGenres() {
        List<Genres> genresList = Arrays.asList(new Genres(), new Genres());
        when(genresRepository.getAllGenres()).thenReturn(genresList);

        List<Genres> result = genresService.getAllGenres();

        assertEquals(genresList, result);
    }

    @Test
    void getGenreById() {
        int genreId = 1;
        Genres genre = new Genres();
        when(genresRepository.get(genreId)).thenReturn(genre);

        Genres result = genresService.get(genreId);

        assertEquals(genre, result);
    }


}
