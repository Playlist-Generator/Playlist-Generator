package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.models.Users;

import java.util.List;

public interface UserService {

    List<Users> get();

    Users get(int id);

    Users get(String username);

    void create(Users users);

}
