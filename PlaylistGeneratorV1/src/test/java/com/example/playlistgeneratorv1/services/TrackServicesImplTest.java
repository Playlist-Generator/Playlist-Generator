package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.TracksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class TrackServicesImplTest {

    @Mock
    private TracksRepository tracksRepository;

    @InjectMocks
    private TrackServicesImpl trackServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTracks() {
        List<Tracks> tracksList = Arrays.asList(new Tracks(), new Tracks());
        when(tracksRepository.get()).thenReturn(tracksList);

        List<Tracks> result = trackServices.get();

        assertEquals(tracksList, result);
    }

    @Test
    void getTrackById() {
        int trackId = 1;
        Tracks track = new Tracks();
        when(tracksRepository.get(trackId)).thenReturn(track);

        Tracks result = trackServices.get(trackId);

        assertEquals(track, result);
    }

    @Test
    void getTrackByTitle() {
        String trackTitle = "TestTrack";
        Tracks track = new Tracks();
        when(tracksRepository.get(trackTitle)).thenReturn(track);

        Tracks result = trackServices.get(trackTitle);

        assertEquals(track, result);
    }

    @Test
    void getTracksByGenre() {
        String genre = "Rock";
        List<Tracks> tracksList = Arrays.asList(new Tracks(), new Tracks());
        when(tracksRepository.getByGenre(genre)).thenReturn(tracksList);

        List<Tracks> result = trackServices.getByGenre(genre);

        assertEquals(tracksList, result);
    }



    @Test
    void updateTrack() {
        Tracks track = new Tracks();

        trackServices.update(track);

        verify(tracksRepository, times(1)).update(track);
    }

    @Test
    void deleteTrack() {
        int trackId = 1;
        User adminUser = new User();
        adminUser.setAdmin(true);

        trackServices.delete(trackId, adminUser);

        verify(tracksRepository, times(1)).delete(trackId);
    }

    @Test
    void deleteTrackThrowsAuthorizationException() {
        int trackId = 1;
        User nonAdminUser = new User();
        nonAdminUser.setAdmin(false);

        assertThrows(AuthorizationException.class, () -> trackServices.delete(trackId, nonAdminUser));
        verify(tracksRepository, never()).delete(anyInt());
    }

}
