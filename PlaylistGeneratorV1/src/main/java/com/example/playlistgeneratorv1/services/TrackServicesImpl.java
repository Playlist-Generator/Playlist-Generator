package com.example.playlistgeneratorv1.services;


import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityLongNotFoundException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.TracksRepository;
import com.example.playlistgeneratorv1.services.contracts.TrackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TrackServicesImpl implements TrackServices {

    public static final String DELETE_AUTHENTICATION_ERROR = "Only admins can delete tracks.";

    private final TracksRepository tracksRepository;

    @Autowired
    public TrackServicesImpl(TracksRepository tracksRepository) {
        this.tracksRepository = tracksRepository;
    }


    @Override
    public List<Tracks> get() {
        return tracksRepository.get();
    }

    @Override
    public Tracks get(int id) {
        return tracksRepository.get(id);
    }

    @Override
    public Tracks get(String title) {
        return tracksRepository.get(title);
    }

    @Override
    public List<Tracks> getByGenre(String genre) {
        return tracksRepository.getByGenre(genre);
    }

    @Override
    public void create(Tracks tracks) {
        boolean duplicateExists = true;
        try {
            tracksRepository.get(tracks.getTitle());

        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("Track", "title", tracks.getTitle());
        }
        tracksRepository.create(tracks);

    }

    @Override
    public void update(Tracks tracks) {
        try {
            tracksRepository.update(tracks);
        } catch (EntityNotFoundException e) {
            throw new EntityLongNotFoundException("Track", tracks.getId());
        }
    }
    @Override
    public List<Tracks> findTopTrackAcrossGenres(int limit) {
        return tracksRepository.findTopTrackAcrossGenres(limit);
    }

    @Override
    public void delete(int id, User user) {

        if (user.isAdmin()) {
            tracksRepository.delete(id);
        }
        else throw new AuthorizationException(DELETE_AUTHENTICATION_ERROR);
    }
}
