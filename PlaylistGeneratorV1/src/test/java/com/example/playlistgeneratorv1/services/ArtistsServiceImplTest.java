package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Artists;
import com.example.playlistgeneratorv1.repositories.contracts.ArtistsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ArtistsServiceImplTest {

    @Mock
    private ArtistsRepository artistsRepository;

    @InjectMocks
    private ArtistsServiceImpl artistsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllArtists() {
        List<Artists> artistsList = Arrays.asList(new Artists(), new Artists());
        when(artistsRepository.getAll()).thenReturn(artistsList);

        List<Artists> result = artistsService.getAllArtists();

        assertEquals(artistsList, result);
    }

    @Test
    void getArtistById() {
        int artistId = 1;
        Artists artist = new Artists();
        when(artistsRepository.getById(artistId)).thenReturn(artist);


        Artists result = artistsService.getArtistById(artistId);


        assertEquals(artist, result);
    }

    @Test
    void getArtistByName() {
        String artistName = "ExampleArtist";
        Artists artist = new Artists();
        when(artistsRepository.getByName(artistName)).thenReturn(artist);


        Artists result = artistsService.getArtistByName(artistName);

        assertEquals(artist, result);
    }

    @Test
    void saveArtist() {
        Artists artist = new Artists();

        artistsService.saveArtist(artist);
    }

    @Test
    void updateArtist() {
        Artists artist = new Artists();

        artistsService.updateArtist(artist);
    }

    @Test
    void deleteArtist() {
        int artistId = 1;

        artistsService.deleteArtist(artistId);
    }
}
