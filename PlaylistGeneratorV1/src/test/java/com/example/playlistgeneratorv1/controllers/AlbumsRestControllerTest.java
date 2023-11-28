package com.example.playlistgeneratorv1.controllers;

import com.example.playlistgeneratorv1.controller.rest.AlbumsRestController;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.helpers.AlbumMapper;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.AlbumDto;
import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.AlbumsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AlbumsRestControllerTest {

    @Mock
    private AlbumsService albumsService;

    @Mock
    private AlbumMapper albumMapper;

    @Mock
    private AuthenticationHelper authenticationHelper;

    @InjectMocks
    private AlbumsRestController albumsRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAlbums() {
        List<Albums> albumsList = Arrays.asList(new Albums(), new Albums());
        when(albumsService.get()).thenReturn(albumsList);

        List<Albums> result = albumsRestController.getAlbums();


        assertEquals(albumsList, result);
    }

    @Test
    void getAlbumById() {

        int albumId = 1;
        Albums album = new Albums();
        when(albumsService.get(albumId)).thenReturn(album);


        Albums result = albumsRestController.getAlbumById(albumId);


        assertEquals(album, result);
    }

    @Test
    void getAlbumByTitle() {

        String title = "Test Album";
        Albums album = new Albums();
        when(albumsService.get(title)).thenReturn(album);


        Albums result = albumsRestController.getAlbumByTitle(title);


        assertEquals(album, result);
    }

    @Test
    void getAlbumsByGenre() {

        String genre = "Rock";
        List<Albums> albumsList = Arrays.asList(new Albums(), new Albums());
        when(albumsService.getByGenre(genre)).thenReturn(albumsList);


        List<Albums> result = albumsRestController.getAlbumsByGenre(genre);


        assertEquals(albumsList, result);
    }


    @Test
    void createAlbum_DuplicateException() {

        AlbumDto albumDto = new AlbumDto();
        when(albumMapper.fromDto(albumDto)).thenReturn(new Albums());
        doThrow(new EntityDuplicateException("Album", "title", "Duplicate Title"))
                .when(albumsService).create(any());


        assertThrows(ResponseStatusException.class, () -> albumsRestController.createAlbum(albumDto));
    }

    @Test
    void updateAlbum() {

        int albumId = 1;
        AlbumDto albumDto = new AlbumDto();
        Albums album = new Albums();
        when(albumMapper.fromDto(eq(albumId), any())).thenReturn(album);
        doNothing().when(albumsService).update(any());


        albumsRestController.updateAlbum(albumId, albumDto);

        verify(albumsService, times(1)).update(any());
    }

    @Test
    void updateAlbum_EntityNotFoundException() {

        int albumId = 1;
        AlbumDto albumDto = new AlbumDto();
        when(albumMapper.fromDto(eq(albumId), any())).thenReturn(new Albums());
        doThrow(new EntityNotFoundException("Album", albumId)).when(albumsService).update(any());


        assertThrows(ResponseStatusException.class, () -> albumsRestController.updateAlbum(albumId, albumDto));
    }

    @Test
    void deleteAlbum() {

        int albumId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        doNothing().when(albumsService).delete(eq(albumId), any());


        albumsRestController.deleteAlbum(albumId, headers);


        verify(albumsService, times(1)).delete(eq(albumId), any());
    }

    @Test
    void deleteAlbum_EntityNotFoundException() {

        int albumId = 1;
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);
        doThrow(new EntityNotFoundException("Album", albumId)).when(albumsService).delete(eq(albumId), any());


        assertThrows(ResponseStatusException.class, () -> albumsRestController.deleteAlbum(albumId, headers));
    }


}
