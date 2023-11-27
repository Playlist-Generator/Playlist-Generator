package com.example.playlistgeneratorv1.controller.rest;

import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.GenresRepository;
import com.example.playlistgeneratorv1.services.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenresController {


    private final AuthenticationHelper authenticationHelper;
    private final GenresService genresService;
    @Autowired
    public GenresController( AuthenticationHelper authenticationHelper, GenresService genresService) {

        this.authenticationHelper = authenticationHelper;
        this.genresService =genresService;
    }

    @GetMapping
    public List<Genres> getAllGenres(@RequestHeader HttpHeaders headers) {
        User user = authenticationHelper.tryGetUser(headers);
        return genresService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Genres get(@RequestHeader HttpHeaders headers,@PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        return genresService.get(id);
    }

}
