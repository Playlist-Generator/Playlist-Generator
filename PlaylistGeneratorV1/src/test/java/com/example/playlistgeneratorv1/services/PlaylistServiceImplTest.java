package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.PlaylistDto;
import com.example.playlistgeneratorv1.repositories.contracts.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlaylistServiceImplTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistServiceImpl playlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPlaylistsByUser() {
        int userId = 1;
        List<Playlists> playlistsList = Arrays.asList(new Playlists(), new Playlists());
        when(playlistRepository.getByUser(userId)).thenReturn(playlistsList);

        List<Playlists> result = playlistService.getByUser(userId);

        assertEquals(playlistsList, result);
    }

    @Test
    void getPlaylistById() {
        int playlistId = 1;
        Playlists playlist = new Playlists();
        when(playlistRepository.get(playlistId)).thenReturn(playlist);

        Playlists result = playlistService.get(playlistId);

        assertEquals(playlist, result);
    }



    @Test
    void updatePlaylist() {
        int playlistId = 1;

        PlaylistDto playlistDto = new PlaylistDto(1,"Default Title","Default Tags",0,0);


        Playlists playlist = new Playlists();
        when(playlistRepository.get(playlistId)).thenReturn(playlist);

        playlistService.update(playlistId, playlistDto);

        verify(playlistRepository, times(1)).update(any());
    }

    @Test
    void deletePlaylist() {
        int playlistId = 1;

        playlistService.delete(playlistId);

        verify(playlistRepository, times(1)).delete(playlistId);
    }




}
