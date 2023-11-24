package com.example.playlistgeneratorv1.repositories;
import com.example.playlistgeneratorv1.models.User;
import java.util.List;

public interface UserRepository {
    List<User> get();
    User get(int id);
    User get(String username);
    void create(User user);
    void delete(int id);


}
