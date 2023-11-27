package com.example.playlistgeneratorv1.controller.rest;

import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.PlaylistDto;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public PlaylistController(PlaylistService playlistService, AuthenticationHelper authenticationHelper) {
        this.playlistService = playlistService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Playlists> getByUser(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        return playlistService.getByUser(user.getId());
    }

    @GetMapping("/{id}")
    public Playlists get(@PathVariable int id) {
        return playlistService.get(id);
    }

    @PostMapping
    public void create(@RequestBody PlaylistDto playlistDto, @RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        playlistService.create(playlistDto, user);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody PlaylistDto playlistDto) {
        playlistService.update(id, playlistDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        playlistService.delete(id);
    }

    @PostMapping("/{playlistId}/addTrack/{trackId}")
    public void addTrack(@PathVariable int playlistId, @PathVariable int trackId) {
        playlistService.addTrack(playlistId, trackId);
    }
}