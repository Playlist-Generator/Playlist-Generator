package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.deezer.*;
import com.example.playlistgeneratorv1.models.*;
import com.example.playlistgeneratorv1.exceptions.GenreSynchronizationFailureException;
import com.example.playlistgeneratorv1.repositories.contracts.AlbumsRepository;
import com.example.playlistgeneratorv1.repositories.contracts.ArtistsRepository;
import com.example.playlistgeneratorv1.repositories.contracts.GenresRepository;
import com.example.playlistgeneratorv1.repositories.contracts.TracksRepository;
import com.example.playlistgeneratorv1.services.contracts.DeezerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DeezerServiceImpl implements DeezerService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    private final GenresRepository genreRepository;

    private final TracksRepository trackRepository;

    private final ArtistsRepository artistRepository;

    private final AlbumsRepository albumRepository;

    @Autowired
    public DeezerServiceImpl(RestTemplate restTemplate, @Value("${deezer.api.base-url}") String baseUrl,
                             GenresRepository genreRepository, TracksRepository trackRepository,
                             ArtistsRepository artistRepository, AlbumsRepository albumRepository) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.genreRepository = genreRepository;
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
            savePlaylistsToDataBase(playlists, genre);
        }
    }

    @Override
    public void getTracks() {

    }

    private void saveGenresToDatabase(List<DeezerGenre> genres) {
        for (DeezerGenre deezerGenre : genres) {
            Genres genre = new Genres();
            if (deezerGenre.getName().equals("All")) {
                continue;
            }
            genre.setId(deezerGenre.getId());
            genre.setGenre(deezerGenre.getName().toLowerCase());
            genreRepository.create(genre);
        }
    }

    private void savePlaylistsToDataBase(List<DeezerPlaylist> playlists, String genre) {
        for (DeezerPlaylist deezerPlaylist : playlists) {
            String trackList = deezerPlaylist.getTrackList();
            DeezerTrackResponse response = restTemplate.getForObject(trackList, DeezerTrackResponse.class);
            if (response != null && response.getData() != null) {
                List<DeezerTrack> tracks = response.getData();
                saveTracksToDataBase(tracks, genre);
            }
        }
    }

    private void saveTracksToDataBase(List<DeezerTrack> tracks, String genre) {
        for (DeezerTrack deezerTrack : tracks) {

            DeezerArtist deezerArtist = deezerTrack.getArtist();
            Artists artist = new Artists();
            artist.setId(deezerArtist.getId());
            artist.setName(deezerArtist.getName());
            artist.setTrackList(deezerArtist.getTrackList());
            artistRepository.create(artist);

            DeezerAlbum deezerAlbum = deezerTrack.getAlbum();
            Albums album = new Albums();
            album.setId(deezerAlbum.getId());
            album.setTitle(deezerAlbum.getTitle());
            album.setTrackList(deezerAlbum.getTrackList());
            albumRepository.create(album);

            Tracks track = new Tracks();
            track.setId(deezerTrack.getId());
            track.setTitle(deezerTrack.getTitle());
            track.setRanks(deezerTrack.getRank());
            track.setDuration(deezerTrack.getDuration());
            track.setPreviewUrl(deezerTrack.getPreview());
            track.setArtist(artist);
            track.setAlbum(album);
            track.setGenre(genreRepository.findByName(genre));
            trackRepository.create(track);
        }

    }
}