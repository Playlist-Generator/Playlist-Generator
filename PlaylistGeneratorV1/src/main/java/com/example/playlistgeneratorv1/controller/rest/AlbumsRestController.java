package com.example.playlistgeneratorv1.controller.rest;

import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.helpers.AlbumMapper;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.AlbumDto;
import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.AlbumsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumsRestController {

    public static final String DELETE_AUTHENTICATION_ERROR = "Only admins can delete albums.";

    private final AlbumsService albumsService;

    private final AlbumMapper albumMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public AlbumsRestController(AlbumsService albumsService,
                                AlbumMapper albumMapper, AuthenticationHelper authenticationHelper) {
        this.albumsService = albumsService;
        this.albumMapper = albumMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Albums> getAlbums() {
        return albumsService.get();
    }

    @GetMapping("/{id}")
    public Albums getAlbumById(@PathVariable int id) {
        Albums album = albumsService.get(id);
        if (album == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found with id: " + id);
        }
        return album;
    }

    @GetMapping("/title/{title}")
    public Albums getAlbumByTitle(@PathVariable String title) {
        Albums album = albumsService.get(title);
        if (album == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Album not found with title: " + title);
        }
        return album;
    }

    @GetMapping("/genre/{genre}")
    public List<Albums> getAlbumsByGenre(@PathVariable String genre) {
        List<Albums> albums = albumsService.getByGenre(genre);
        if (albums == null || albums.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Albums with genre not found: " + genre);
        }
        return albums;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAlbum(@Valid @RequestBody AlbumDto albumDto) {
        try {
            Albums album = albumMapper.fromDto(albumDto);
            albumsService.create(album);
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateAlbum(@PathVariable int id, @RequestBody AlbumDto albumDto) {
        Albums album = albumMapper.fromDto(id, albumDto);
        album.setId(id);
        try {
            albumsService.update(album);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            albumsService.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}