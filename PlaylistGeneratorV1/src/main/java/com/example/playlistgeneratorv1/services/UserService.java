package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.User;

import java.util.List;

public interface UserService {

    List<User> get();

    User get(int id);

    User get(String username);

    void create(User user);

}
