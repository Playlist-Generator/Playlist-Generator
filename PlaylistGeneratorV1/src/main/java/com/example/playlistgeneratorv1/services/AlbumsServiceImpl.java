package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.AlbumsRepository;
import com.example.playlistgeneratorv1.services.contracts.AlbumsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumsServiceImpl implements AlbumsService {

    public static final String DELETE_AUTHENTICATION_ERROR = "Only admins can delete albums.";

    private final AlbumsRepository albumsRepository;

    @Autowired
    public AlbumsServiceImpl(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }

    @Override
    public List<Albums> get() {
        return albumsRepository.get();
    }

    @Override
    public Albums get(int id) {
        return albumsRepository.get(id);
    }

    @Override
    public Albums get(String title) {
        return albumsRepository.get(title);
    }

    @Override
    public List<Albums> getByGenre(String genre) {
        return albumsRepository.getByGenre(genre);
    }

    @Override
    public void create(Albums album) {
        boolean duplicateExists = true;
        try {
            albumsRepository.get(album.getTitle());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("Album", "title", album.getTitle());
        }
        albumsRepository.create(album);
    }

    @Override
    public void update(Albums album) {
        try {
            albumsRepository.update(album);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Album", album.getId());
        }
    }

    @Override
    public void delete(int id, User user) {
        if (user.isAdmin()) {
            albumsRepository.delete(id);
        } else {
            throw new AuthorizationException(DELETE_AUTHENTICATION_ERROR);
        }
    }
}

