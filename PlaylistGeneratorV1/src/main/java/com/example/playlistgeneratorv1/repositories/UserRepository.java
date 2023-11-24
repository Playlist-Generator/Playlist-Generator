package com.example.playlistgeneratorv1.repositories;
import com.example.playlistgeneratorv1.models.Users;
import java.util.List;

public interface UserRepository {
    List<Users> get();
    Users get(int id);
    Users get(String username);
    void create(Users users);
    void update(Users users);
}
