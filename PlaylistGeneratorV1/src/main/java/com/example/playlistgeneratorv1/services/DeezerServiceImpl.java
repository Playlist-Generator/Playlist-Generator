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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeezerServiceImpl implements DeezerService {

    private final DeezerApiClient deezerApiClient;
    private final GenresRepository genresRepository;
    private final TracksRepository tracksRepository;
    private final ArtistsRepository artistRepository;

    private final AlbumsRepository albumsRepository;

    @Autowired
    public DeezerServiceImpl(DeezerApiClient deezerApiClient, GenresRepository genresRepository,
                             TracksRepository tracksRepository, ArtistsRepository artistRepository,
                             AlbumsRepository albumsRepository) {
        this.deezerApiClient = deezerApiClient;
        this.genresRepository = genresRepository;
        this.tracksRepository = tracksRepository;
        this.artistRepository = artistRepository;
        this.albumsRepository = albumsRepository;
    }

    public void preFetchGenres() {
        List<String> genres = deezerApiClient.fetchGenres();

        for (String genre : genres) {
            Genres genresObj = new Genres();
            genresObj.setGenre(genre);
            genresRepository.create(genresObj);
        }
    }

    public void preFetchTracks() {
        List<Genres> genresList = genresRepository.getAllGenres();

        for (Genres genre : genresList) {
            List<Tracks> tracks = deezerApiClient.fetchTracksByGenre(genre.getGenre(), 1000);

            for (Tracks track : tracks) {
                Artists artistObj = artistRepository.getByName(track.getArtist().getName());
                if (artistObj == null) {
                    artistObj = track.getArtist();
                    artistRepository.create(artistObj);
                }

                track.setGenre(genre);
                track.setArtist(artistObj);

                tracksRepository.create(track);
            }
        }
    }

}