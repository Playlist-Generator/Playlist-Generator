package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.PlaylistDto;

import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.PlaylistRepository;
import com.example.playlistgeneratorv1.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Playlists> getByUser(int userId) {
        return playlistRepository.getByUser(userId);
    }

    @Override
    public Playlists get(int id) {
        return playlistRepository.get(id);
    }

    @Override
    public void create(PlaylistDto playlistDto, User user) {
        Playlists playlist = new Playlists();
        playlist.setUser(user);
        playlist.setTitle(playlistDto.getTitle());

        playlistRepository.create(playlist);
    }

    @Override
    public void update(int id, PlaylistDto playlistDto) {
        Playlists playlist = playlistRepository.get(id);
        if (playlist != null) {
            playlist.setTitle(playlistDto.getTitle());
            playlistRepository.update(playlist);
        } else {
            throw new EntityNotFoundException("Playlists", id);
        }
    }

    @Override
    public void delete(int id) {
        playlistRepository.delete(id);
    }

//    @Override
//    public void addTrack(int playlistId, int trackId) {
//        playlistRepository.addTrack(playlistId, trackId);
//    }
//    @Override
//    public void removeTrack(int playlistId, int trackId) {
//        playlistRepository.removeTrack(playlistId, trackId);
//    }
}
