package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.deezer.*;
import com.example.playlistgeneratorv1.exceptions.GenreSynchronizationFailureException;
import com.example.playlistgeneratorv1.models.*;
import com.example.playlistgeneratorv1.repositories.contracts.*;
import com.example.playlistgeneratorv1.repositories.contracts.PlaylistRepository;
import com.example.playlistgeneratorv1.services.contracts.DeezerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.Optional;

import java.util.List;

import java.util.stream.Stream;

@Service
public class DeezerServiceImpl implements DeezerService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    private final GenresRepository genreRepository;
    private final PlaylistRepository playlistRepository;
    private final TracksRepository trackRepository;
    private final ArtistsRepository artistRepository;
    private final AlbumsRepository albumRepository;

    @Autowired
    public DeezerServiceImpl(RestTemplate restTemplate, @Value("${deezer.api.base-url}") String baseUrl,
                             GenresRepository genreRepository, PlaylistRepository playlistRepository,
                             TracksRepository trackRepository, ArtistsRepository artistRepository,
                             AlbumsRepository albumRepository) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.genreRepository = genreRepository;
        this.playlistRepository = playlistRepository;
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public void getGenres() {
        String genresEndpoint = baseUrl + "/genre";
        DeezerGenreResponse response = restTemplate.getForObject(genresEndpoint, DeezerGenreResponse.class);
        if (response != null && response.getData() != null) {
            List<DeezerGenre> genres = response.getData();
            saveGenresToDatabase(genres);
        } else {
            throw new GenreSynchronizationFailureException("Genre synchronization failed.");
        }
    }

    @Override
    public void getPlaylists(String genre) {
        String playlistsEndpoint = baseUrl + "/search/playlist?q=" + genre + "&limit=10";
        DeezerPlaylistResponse response = restTemplate.getForObject(playlistsEndpoint, DeezerPlaylistResponse.class);
        if (response != null && response.getData() != null) {
            List<DeezerPlaylist> playlists = response.getData();
            savePlaylistsToDatabase(playlists, genre);
        }
    }

    @Override
    public void getTracks() {
        // Implementation for getting tracks goes here
    }

    private void saveGenresToDatabase(List<DeezerGenre> genres) {
        genres.stream()
                .filter(genre -> !genre.getName().equals("All"))
                .forEach(this::saveGenreToDatabase);
    }

    private void saveGenreToDatabase(DeezerGenre deezerGenre) {
        Genres genre = Stream.of(deezerGenre)
                .map(g -> {
                    Genres newGenre = new Genres();
                    newGenre.setId(g.getId());
                    newGenre.setGenre(g.getName().toLowerCase());
                    return newGenre;
                })
                .findFirst()
                .orElse(null);

        genreRepository.create(genre);
    }

    private void savePlaylistsToDatabase(List<DeezerPlaylist> playlists, String genre) {
        playlists.forEach(deezerPlaylist -> {
            String trackList = deezerPlaylist.getTrackList();
            DeezerTrackResponse response = restTemplate.getForObject(trackList, DeezerTrackResponse.class);
            if (response != null && response.getData() != null) {
                List<DeezerTrack> tracks = response.getData();
                saveTracksToDatabase(tracks, genre);
            }
        });
    }

    private void saveTracksToDatabase(List<DeezerTrack> tracks, String genre) {
        tracks.forEach(deezerTrack -> {
            Artists artist = saveArtistToDatabase(deezerTrack.getArtist());
            Albums album = saveAlbumToDatabase(deezerTrack.getAlbum());

            Tracks track = new Tracks();
            track.setId(deezerTrack.getId());
            track.setTitle(deezerTrack.getTitle());
            track.setRanks(deezerTrack.getRank());
            track.setPreviewUrl(deezerTrack.getPreview());
            track.setArtist(artist);
            track.setAlbum(album);
            track.setGenre(genreRepository.findByName(genre));
            int durationInSeconds = deezerTrack.getDuration();
            Time durationTime = new Time(durationInSeconds);
            track.setDuration(durationTime);
            track.setMd5_image(deezerTrack.getMd5_image());
            trackRepository.create(track);
        });
    }

    private Artists saveArtistToDatabase(DeezerArtist deezerArtist) {
        Artists artist = new Artists();
        Optional.ofNullable(deezerArtist) .ifPresent(deezer ->
        {
            artist.setId(deezer.getId());
            artist.setName(deezer.getName());
            artist.setTrackList(deezer.getTrackList());
            artist.setPhotoUrl(deezer.getPhotoUrl());

        });
            artistRepository.create(artist);
        return artist;
    }

    private Albums saveAlbumToDatabase(DeezerAlbum deezerAlbum) {
        Albums album = new Albums();
        album.setId(deezerAlbum.getId());
        album.setTitle(deezerAlbum.getTitle());
        album.setTrackList(deezerAlbum.getTrackList());
        album.setPhotoUrl(deezerAlbum.getPhotoUrl());
        albumRepository.create(album);
        return album;
    }
}
