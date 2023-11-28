package com.example.playlistgeneratorv1.controller.rest;

import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Artists;

import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistsController {

    private final ArtistsService artistsService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ArtistsController(ArtistsService artistsService, AuthenticationHelper authenticationHelper) {
        this.artistsService = artistsService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Artists> getAllArtists() {
        return artistsService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artists> getArtistById(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        Artists artist = artistsService.getArtistById(id);
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Artists> getArtistByName(@PathVariable String name, @RequestHeader HttpHeaders headers) {
        Artists artist = artistsService.getArtistByName(name);
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Artists> saveArtist(@RequestBody Artists artist, @RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        Artists savedArtist = artistsService.saveArtist(artist);
        return new ResponseEntity<>(savedArtist, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArtist(@PathVariable int id, @RequestBody Artists artist, @RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        artist.setId(id);
        artistsService.updateArtist(artist);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable int id,@RequestBody Artists artist, @RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        artistsService.deleteArtist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
