package com.example.playlistgeneratorv1.controllers;

import com.example.playlistgeneratorv1.controller.rest.ArtistsController;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Artists;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.ArtistsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArtistsControllerTest {

    @Mock
    private ArtistsService artistsService;

    @Mock
    private AuthenticationHelper authenticationHelper;

    @InjectMocks
    private ArtistsController artistsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllArtists() {

        List<Artists> artistsList = Arrays.asList(new Artists(), new Artists());
        when(artistsService.getAllArtists()).thenReturn(artistsList);


        List<Artists> result = artistsController.getAllArtists();


        assertEquals(artistsList, result);
    }

    @Test
    void getArtistById() {

        int artistId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        when(artistsService.getArtistById(artistId)).thenReturn(new Artists());


        ResponseEntity<Artists> result = artistsController.getArtistById(artistId, headers);


        assertEquals(HttpStatus.OK, result.getStatusCode());
    }



    @Test
    void getArtistByName() {

        String artistName = "Test Artist";
        when(artistsService.getArtistByName(artistName)).thenReturn(new Artists());


        ResponseEntity<Artists> result = artistsController.getArtistByName(artistName, new HttpHeaders());


        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void saveArtist() {

        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        Artists artist = new Artists();
        when(artistsService.saveArtist(artist)).thenReturn(artist);


        ResponseEntity<Artists> result = artistsController.saveArtist(artist, headers);


        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(artist, result.getBody());
    }






    @Test
    void deleteArtist() {

        int artistId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);

        ResponseEntity<Void> result = artistsController.deleteArtist(artistId, new Artists(), headers);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }


}
