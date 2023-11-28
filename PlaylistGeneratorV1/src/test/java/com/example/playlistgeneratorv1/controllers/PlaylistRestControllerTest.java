package com.example.playlistgeneratorv1.controllers;

import com.example.playlistgeneratorv1.controller.rest.PlaylistRestController;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.PlaylistService;
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

class PlaylistRestControllerTest {

    @Mock
    private PlaylistService playlistService;

    @Mock
    private AuthenticationHelper authenticationHelper;

    @InjectMocks
    private PlaylistRestController playlistRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByUser() {
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        List<Playlists> playlistsList = Arrays.asList(new Playlists(), new Playlists());
        when(playlistService.getByUser(user.getId())).thenReturn(playlistsList);

        List<Playlists> result = playlistRestController.getByUser(headers);

        assertEquals(playlistsList, result);
    }

    @Test
    void get() {
        int playlistId = 1;
        Playlists playlist = new Playlists();
        when(playlistService.get(playlistId)).thenReturn(playlist);

        Playlists result = playlistRestController.get(playlistId);

        assertEquals(playlist, result);
    }






    @Test
    void delete() {

        int playlistId = 1;

        playlistRestController.delete(playlistId);

        verify(playlistService, times(1)).delete(playlistId);
    }
}
