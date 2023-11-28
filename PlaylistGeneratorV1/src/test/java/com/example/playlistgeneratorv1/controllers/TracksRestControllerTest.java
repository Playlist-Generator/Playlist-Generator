package com.example.playlistgeneratorv1.controllers;

import com.example.playlistgeneratorv1.controller.rest.TracksRestController;
import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.TrackServicesImpl;
import com.example.playlistgeneratorv1.services.contracts.TrackServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TracksRestControllerTest {

    @Mock
    private TrackServices trackServices;

    @Mock
    private AuthenticationHelper authenticationHelper;

    @InjectMocks
    private TracksRestController tracksRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get() {

        List<Tracks> tracksList = Arrays.asList(new Tracks(), new Tracks());
        when(trackServices.get()).thenReturn(tracksList);


        List<Tracks> result = tracksRestController.get();


        assertEquals(tracksList, result);
    }

    @Test
    void getById() {

        int trackId = 1;
        Tracks track = new Tracks();
        when(trackServices.get(trackId)).thenReturn(track);


        Tracks result = tracksRestController.get(trackId);


        assertEquals(track, result);
    }

    @Test
    void getById_NonExistingTrack() {

        int trackId = 1;
        when(trackServices.get(trackId)).thenReturn(null);


        assertThrows(ResponseStatusException.class, () -> tracksRestController.get(trackId));
    }

    @Test
    void getByTitle() {

        String title = "Test Track";
        Tracks track = new Tracks();
        when(trackServices.get(title)).thenReturn(track);


        Tracks result = tracksRestController.get(title);


        assertEquals(track, result);
    }

    @Test
    void getByTitle_NonExistingTrack() {

        String title = "NonExistingTrack";
        when(trackServices.get(title)).thenReturn(null);


        assertThrows(ResponseStatusException.class, () -> tracksRestController.get(title));
    }

    @Test
    void getByGenre() {

        String genre = "Rock";
        List<Tracks> tracksList = Arrays.asList(new Tracks(), new Tracks());
        when(trackServices.getByGenre(genre)).thenReturn(tracksList);

        List<Tracks> result = tracksRestController.getTracksByGenre(genre);

        assertEquals(tracksList, result);
    }

    @Test
    void getByGenre_NonExistingTracks() {
        String genre = "NonExistingGenre";
        when(trackServices.getByGenre(genre)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> tracksRestController.getTracksByGenre(genre));
    }

    @Test
    void create() {
        Tracks track = new Tracks();

        tracksRestController.create(track);

        verify(trackServices, times(1)).create(track);
    }

    @Test
    void create_DuplicateTrack() {

        Tracks track = new Tracks();
        doThrow(new EntityDuplicateException("Track", "title", track.getTitle()))
                .when(trackServices).create(track);

        assertThrows(ResponseStatusException.class, () -> tracksRestController.create(track));
    }

    @Test
    void update() {
        int trackId = 1;
        Tracks track = new Tracks();
        track.setId(trackId);

        tracksRestController.update(trackId, track);

        verify(trackServices, times(1)).update(track);
    }

    @Test
    void update_NonExistingTrack() {
        int trackId = 1;
        Tracks track = new Tracks();
        track.setId(trackId);
        doThrow(new EntityNotFoundException("Track", trackId))
                .when(trackServices).update(track);

        assertThrows(ResponseStatusException.class, () -> tracksRestController.update(trackId, track));
    }

    @Test
    void delete() {
        int trackId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);

        tracksRestController.delete(trackId, headers);

        verify(trackServices, times(1)).delete(trackId, user);
    }

    @Test
    void delete_NonExistingTrack() {
        int trackId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        doThrow(new EntityNotFoundException("Track", trackId))
                .when(trackServices).delete(trackId, user);

        assertThrows(ResponseStatusException.class, () -> tracksRestController.delete(trackId, headers));
    }

    @Test
    void delete_Unauthorized() {
        int trackId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setAdmin(false);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        doThrow(new AuthorizationException(TrackServicesImpl.DELETE_AUTHENTICATION_ERROR))
                .when(trackServices).delete(trackId, user);

        assertThrows(ResponseStatusException.class, () -> tracksRestController.delete(trackId, headers));
    }
}
