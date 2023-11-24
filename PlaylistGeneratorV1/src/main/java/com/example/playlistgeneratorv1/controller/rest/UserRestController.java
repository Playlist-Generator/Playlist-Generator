package com.example.playlistgeneratorv1.controller.rest;


import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.Users;
import com.example.playlistgeneratorv1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public List<Users> get(@RequestHeader HttpHeaders headers) {
        try {
            Users users = authenticationHelper.tryGetUser(headers);
            if (!users.isAdmin()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ERROR_MESSAGE);
            }
            return service.get();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Users get(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            Users users = authenticationHelper.tryGetUser(headers);
            checkAccessPermissions(id, users);
            return service.get(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }







    private static void checkAccessPermissions(int targetUserId, Users executingUsers) {
        if (!executingUsers.isAdmin() && executingUsers.getId() != targetUserId) {
            throw new AuthorizationException(ERROR_MESSAGE);
        }
    }

}


