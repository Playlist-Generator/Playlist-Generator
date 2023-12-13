package com.example.playlistgeneratorv1.controller.rest;


import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.exceptions.UnauthorizedOperationException;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.PlaylistFilterOptions;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/playlists")
public class PlaylistRestController {
    private PlaylistService playlistService;
    private final AuthenticationHelper authenticationHelper;


    @Autowired
    public PlaylistRestController(PlaylistService playlistService, AuthenticationHelper authenticationHelper) {
        this.playlistService = playlistService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Playlists> get(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Time duration,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        PlaylistFilterOptions playlistFilterOptions = new PlaylistFilterOptions
                (name, duration, genre, sortBy, sortOrder);
        return playlistService.get(playlistFilterOptions);
    }

    @DeleteMapping("{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            playlistService.delete(user, id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/new/{origin}/{destination}")
    public List<Tracks> getGeneratedPlaylist(
            @RequestBody Map<String, Integer> genrePercentages,
            @PathVariable String origin,
            @PathVariable String destination) {
        return playlistService.generatePlaylist(genrePercentages, origin, destination);

    }
    @GetMapping("/{id}")
    public Playlists getById(@PathVariable int id) {
        try {
            return playlistService.getByPlaylistId(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{name}")
    public void create(
            @RequestHeader HttpHeaders headers,
            @PathVariable String name,
            @RequestBody Map<String, Integer> genrePercentages,
            @RequestParam String origin,
            @RequestParam String destination) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            playlistService.create(name, user, genrePercentages, origin, destination);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
