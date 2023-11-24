package com.example.playlistgeneratorv1.services;
import com.example.playlistgeneratorv1.exceptions.EntityDuplicateException;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> get() {
        return repository.get();
    }

    @Override
    public User get(int id) {
        return repository.get(id);
    }

    @Override
    public User get(String username) {
        return repository.get(username);
    }

    @Override
    public void create(User user) {
        boolean duplicateExists = true;
        try {
            repository.get(user.getUsername());

        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }

        repository.create(user);
    }}
