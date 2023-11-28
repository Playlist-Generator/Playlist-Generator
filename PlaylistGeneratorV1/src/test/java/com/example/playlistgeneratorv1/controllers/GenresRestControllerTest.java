package com.example.playlistgeneratorv1.controllers;

import com.example.playlistgeneratorv1.controller.rest.GenresRestController;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.GenresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GenresRestControllerTest {

    @Mock
    private AuthenticationHelper authenticationHelper;

    @Mock
    private GenresService genresService;

    @InjectMocks
    private GenresRestController genresRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGenres() {
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        List<Genres> genresList = Arrays.asList(new Genres(), new Genres());
        when(genresService.getAllGenres()).thenReturn(genresList);

        List<Genres> result = genresRestController.getAllGenres(headers);

        assertEquals(genresList, result);
    }

    @Test
    void get() {
        int genreId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        Genres genre = new Genres();
        when(genresService.get(genreId)).thenReturn(genre);

        Genres result = genresRestController.get(headers, genreId);

        assertEquals(genre, result);
    }


}
