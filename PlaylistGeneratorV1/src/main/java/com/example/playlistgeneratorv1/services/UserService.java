package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.RegisterDto;
import com.example.playlistgeneratorv1.models.User;

import java.util.List;

public interface UserService {

    List<User> get();

    User get(int id);

    User get(String username);

    User create(RegisterDto registerDto);
    void update(int id, User updatedUser);

    void delete(int id, User user);
}
