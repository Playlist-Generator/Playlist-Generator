package com.example.playlistgeneratorv1.controller.rest;


import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.RegisterDto;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    public static final String ERROR_MESSAGE = "You are not authorized to browse user information.";
    private final UserService service;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserRestController(UserService service, AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<User> get(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            if (!user.isAdmin()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE);
            }
            return service.get();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User get(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            checkAccessPermissions(id, user);
            return service.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<User> create(@RequestBody RegisterDto registerDto) {
        try {
            User newUser = service.create(registerDto);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id, user);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }



    private static void checkAccessPermissions(int targetUserId, User executingUser) {
        if (!executingUser.isAdmin() && executingUser.getId() != targetUserId) {
            throw new AuthorizationException(ERROR_MESSAGE);
        }
    }

}


