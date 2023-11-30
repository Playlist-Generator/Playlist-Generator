package com.example.playlistgeneratorv1.controller.rest;

import com.example.playlistgeneratorv1.services.contracts.DeezerService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deezer")
@Hidden
public class DeezerRestController {

    private final DeezerService deezerService;

    @Autowired
    public DeezerRestController(DeezerService deezerService) {
        this.deezerService = deezerService;
    }

    @GetMapping("/genres")
    public void getGenres() {
        deezerService.getGenres();
    }

    @GetMapping(value = "/playlists")
    public void getPlaylists(@RequestParam(required = true) String genre){ deezerService.
            getPlaylists(genre.toLowerCase());}
}
