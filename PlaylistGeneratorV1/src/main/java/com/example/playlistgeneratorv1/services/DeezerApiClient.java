package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.models.Artists;
import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.repositories.contracts.AlbumsRepository;
import com.example.playlistgeneratorv1.repositories.contracts.ArtistsRepository;
import com.example.playlistgeneratorv1.repositories.contracts.GenresRepository;
import com.example.playlistgeneratorv1.repositories.contracts.TracksRepository;
import com.example.playlistgeneratorv1.services.contracts.DeezerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeezerApiClient {

    private static final int TRACKS_PER_GENRE = 1000;

    private final DeezerService deezerService;
    private final GenresRepository genresRepository;
    private final TracksRepository tracksRepository;
    private final ArtistsRepository artistsRepository;
    private final AlbumsRepository albumsRepository;

    @Autowired
    public DeezerApiClient(DeezerService deezerService, GenresRepository genresRepository,
                           TracksRepository tracksRepository, ArtistsRepository artistsRepository,
                           AlbumsRepository albumsRepository) {
        this.deezerService = deezerService;
        this.genresRepository = genresRepository;
        this.tracksRepository = tracksRepository;
        this.artistsRepository = artistsRepository;
        this.albumsRepository = albumsRepository;
    }

    public void fetchAndStoreGenres() {
        //
        List<String> genres = fetchGenres();

        //
        for (String genreName : genres) {
            Genres genre = new Genres();
            genresRepository.create(genre);
        }
    }

    public void fetchAndStoreTracksForGenres() {
        //
        List<Genres> genres = genresRepository.getAllGenres();

        //
        for (Genres genre : genres) {
            //
            List<Tracks> tracks = fetchTracksByGenre(genre.getGenre(), TRACKS_PER_GENRE);

            //
            for (Tracks track : tracks) {
                //
                Artists artist = fetchOrCreateArtist(track.getArtist());

                //
                Albums album = fetchOrCreateAlbum(track.getAlbum());

                //
                track.setGenre(genre);
                track.setArtist(artist);
                track.setAlbum(album);
                tracksRepository.create(track);
            }
        }
    }

    List<String> fetchGenres() {
        //
        List<String> genres = new ArrayList<>();

        //
        // ...

        return genres;
    }

    public List<Tracks> fetchTracksByGenre(String genreName, int limit) {

        List<Tracks> tracks = new ArrayList<>();




        return tracks;
    }

    public Artists fetchOrCreateArtist(Artists artist) {

        Artists existingArtist = artistsRepository.getByName(artist.getName());


        if (existingArtist != null) {
            return existingArtist;
        }


        artistsRepository.create(artist);
        return artist;
    }

    public Albums fetchOrCreateAlbum(Albums album) {

        Albums existingAlbum = albumsRepository.get(album.getId());


        if (existingAlbum != null) {
            return existingAlbum;
        }


        albumsRepository.create(album);
        return album;
    }
}