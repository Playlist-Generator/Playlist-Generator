package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.PlaylistFilterOptions;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.PlaylistRepository;
import com.example.playlistgeneratorv1.repositories.contracts.TracksRepository;
import com.example.playlistgeneratorv1.services.contracts.PlaylistService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.playlistgeneratorv1.helpers.CheckPermissions;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;



@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final SessionFactory sessionFactory;
    private final TravelTimeService travelTimeService;
    private final TracksRepository trackRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, SessionFactory sessionFactory, TravelTimeService travelTimeService, TracksRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.sessionFactory = sessionFactory;
        this.travelTimeService = travelTimeService;
        this.trackRepository = trackRepository;
    }

    @Override
    public List<Playlists> getAll() {
        return playlistRepository.getAll();
    }

    @Override
    public List<Playlists> get(PlaylistFilterOptions playlistFilterOptions) {
        return playlistRepository.get(playlistFilterOptions);
    }

    @Override
    public Playlists getByPlaylistId(int id) {
        return playlistRepository.getByPlaylistId(id);
    }
    public List<Playlists> getUsersPlaylists(User user){
        return playlistRepository.getUsersPlaylists(user);
    }

    @Override
    public List<Playlists> getHighestRankPlaylist() {
        return playlistRepository.getHighestRank();
    }

    @Override
    public List<Tracks> generatePlaylist(Map<String, Integer> genrePercentages, String origin, String destination) {
        List<Tracks> selectedTracks = new ArrayList<>();
        int playlistDuration = 0;

        Time duration = travelTimeService.getTravelTime(origin, destination);
        int travelDuration = (duration.getHours() * 3600) + (duration.getMinutes() * 60) + duration.getSeconds();


        List<String> allGenres = trackRepository.getAllGenresFromTracks(trackRepository.getAll());
        Map<String, List<Tracks>> tracksByGenre = trackRepository.getTracksByGenres(allGenres);

        Random random = new Random();

        for (Map.Entry<String, Integer> entry : genrePercentages.entrySet()) {
            String genre = entry.getKey();
            int percentage = entry.getValue();

            if (tracksByGenre.containsKey(genre)) {
                List<Tracks> genreTracks = tracksByGenre.get(genre);
                Collections.shuffle(genreTracks);

                int genreDuration = 0;
                int targetGenreDuration = (int) ((percentage / 100.0) * travelDuration);

                for (Tracks track : genreTracks) {
                    if (genreDuration + track.getDuration().toLocalTime().getSecond() + track.getDuration().toLocalTime().getMinute() * 60 <= targetGenreDuration) {
                        selectedTracks.add(track);
                        genreDuration += (track.getDuration().toLocalTime().getMinute() * 60) + track.getDuration().toLocalTime().getSecond();
                    } else {
                        break;
                    }
                }

                playlistDuration += genreDuration;
            }
        }

        while (playlistDuration < travelDuration - 300 || playlistDuration > travelDuration + 300) {
            String randomGenre = new ArrayList<>(genrePercentages.keySet()).get(random.nextInt(genrePercentages.size()));

            if (tracksByGenre.containsKey(randomGenre)) {
                List<Tracks> randomGenreTracks = tracksByGenre.get(randomGenre);
                Collections.shuffle(randomGenreTracks);

                Tracks randomTrack = randomGenreTracks.get(0);

                selectedTracks.add(randomTrack);
                playlistDuration += (randomTrack.getDuration().toLocalTime().getMinute()*60)+randomTrack.getDuration().toLocalTime().getSecond();
            }
        }

        return selectedTracks;
    }

    private Time playlistDuration(List<Tracks> selectedTracks) {
        int seconds=0;

        for (Tracks track : selectedTracks) {
            seconds += track.getDuration().getSeconds()+(track.getDuration().getMinutes()*60);
        }
        // Convert seconds to LocalTime
        LocalTime localTime = LocalTime.ofSecondOfDay(seconds);

        // Convert LocalTime to Time
        Time time = Time.valueOf(localTime);

        return time;

    }


    @Override
    public int showPostsCount() {
        return playlistRepository.getAllCount().size();
    }

    @Override
    public void create(String name, User user,
                       Map<String, Integer> genrePercentages,
                       String origin, String destination) {
        Playlists playlist = new Playlists();
       CheckPermissions.checkUserAuthorization(user.getId(), user);
        List<Tracks> selectedTracks = generatePlaylist(genrePercentages, origin, destination);
        playlist.setDuration(playlistDuration(selectedTracks));
        playlist.setName(name);
        playlist.setTracks(selectedTracks);
        playlist.setCreatedBy(user);
        playlist.setRank(rankPlaylist(selectedTracks));
        playlistRepository.create(playlist);
    }

    private int rankPlaylist(List<Tracks> selectedTracks) {
        int totalRank = 0;
        int counter = 0;
        for (Tracks track : selectedTracks) {
            counter++;
            if(track.getRanks()>0) {
                totalRank += track.getRanks();
            }
        }
        int rankPlaylist = totalRank / counter;
        return rankPlaylist;
    }

    @Override
    public void update(User user, Playlists playlist) {
        CheckPermissions.checkIfSameUserOrAdmin(user, playlist);

        playlistRepository.update(playlist);
    }

    @Override
    public void delete(User user, int playlistId) {
        Playlists playlist = getByPlaylistId(playlistId);
        CheckPermissions.checkIfSameUserOrAdmin(user, playlist);

        playlistRepository.delete(playlistId);
    }
}
