package com.example.playlistgeneratorv1.controller.rest;


import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.Users;
import com.example.playlistgeneratorv1.services.TrackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TracksRestController {

    public static final String DELETE_AUTHENTICATION_ERROR = "Only admins can delete tracks.";

    private final TrackServices trackServices;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TracksRestController(TrackServices trackServices, AuthenticationHelper authenticationHelper) {
        this.trackServices = trackServices;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Tracks> get() {
        return trackServices.get();
    }

    @GetMapping("/{id}")
    public Tracks get(@PathVariable int id) {
        Tracks track = trackServices.get(id);
        if (track == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Track not found with id: " + id);
        }
        return track;
    }

    @GetMapping("/genre/{genre}")
    public List<Tracks> getTracksByGenre(@PathVariable String genre) {
       List<Tracks> track = trackServices.getByGenre(genre);
        if (track == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tracks with genre not found: " + genre);
        }
        return track;
    }



    @PostMapping
    public void create(@RequestBody Tracks track) {
        try {
            trackServices.create(track);
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Tracks track) {
        track.setId(id);
        try {
            trackServices.update(track);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try {
            Users user = authenticationHelper.tryGetUser(headers);
            trackServices.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
