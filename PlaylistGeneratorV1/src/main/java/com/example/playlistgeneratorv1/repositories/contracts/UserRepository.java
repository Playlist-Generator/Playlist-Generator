package com.example.playlistgeneratorv1.repositories.contracts;
import com.example.playlistgeneratorv1.models.User;
import java.util.List;

public interface UserRepository {
    List<User> get();
    User get(int id);
    User get(String username);
    void create(User user);
    void update(int id, User updatedUser);
    void delete(int id);


    User getByEmail(String email);
}
