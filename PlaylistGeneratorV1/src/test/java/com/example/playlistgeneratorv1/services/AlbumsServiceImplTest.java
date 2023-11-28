package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.AlbumsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AlbumsServiceImplTest {

    @Mock
    private AlbumsRepository albumsRepository;

    @InjectMocks
    private AlbumsServiceImpl albumsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAlbums() {
        List<Albums> albumsList = Arrays.asList(new Albums(), new Albums());
        when(albumsRepository.get()).thenReturn(albumsList);

        List<Albums> result = albumsService.get();

        assertEquals(albumsList, result);
    }

    @Test
    void getAlbumById() {
        int albumId = 1;
        Albums album = new Albums();
        when(albumsRepository.get(albumId)).thenReturn(album);

        Albums result = albumsService.get(albumId);

        assertEquals(album, result);
    }

    @Test
    void getAlbumByTitle() {
        String title = "Test Album";
        Albums album = new Albums();
        when(albumsRepository.get(title)).thenReturn(album);

        Albums result = albumsService.get(title);

        assertEquals(album, result);
    }

    @Test
    void getAlbumsByGenre() {
        String genre = "Rock";
        List<Albums> albumsList = Arrays.asList(new Albums(), new Albums());
        when(albumsRepository.getByGenre(genre)).thenReturn(albumsList);

        List<Albums> result = albumsService.getByGenre(genre);

        assertEquals(albumsList, result);
    }

    @Test
    void createAlbum() {
        Albums newAlbum = new Albums();
        when(albumsRepository.get(newAlbum.getTitle())).thenThrow(EntityNotFoundException.class);

        albumsService.create(newAlbum);

        verify(albumsRepository, times(1)).create(newAlbum);
    }

    @Test
    void createAlbumThrowsEntityDuplicateException() {
        Albums existingAlbum = new Albums();
        when(albumsRepository.get(existingAlbum.getTitle())).thenReturn(existingAlbum);

        assertThrows(EntityDuplicateException.class, () -> albumsService.create(existingAlbum));
        verify(albumsRepository, never()).create(any());
    }

    @Test
    void updateAlbum() {
        Albums updateAlbum = new Albums();
        Albums existingAlbum = new Albums();
        when(albumsRepository.get(updateAlbum.getId())).thenReturn(existingAlbum);

        albumsService.update(updateAlbum);

        verify(albumsRepository, times(1)).update(updateAlbum);
    }



    @Test
    void deleteAlbum() {
        int albumId = 1;
        Albums existingAlbum = new Albums();
        User adminUser = new User();
        adminUser.setAdmin(true);

        when(albumsRepository.get(albumId)).thenReturn(existingAlbum);

        albumsService.delete(albumId, adminUser);

        verify(albumsRepository, times(1)).delete(albumId);
    }

    @Test
    void deleteAlbumThrowsAuthorizationException() {
        int albumId = 1;
        Albums existingAlbum = new Albums();
        User nonAdminUser = new User();
        nonAdminUser.setAdmin(false);

        when(albumsRepository.get(albumId)).thenReturn(existingAlbum);

        assertThrows(AuthorizationException.class, () -> albumsService.delete(albumId, nonAdminUser));
        verify(albumsRepository, never()).delete(anyInt());
    }


}
