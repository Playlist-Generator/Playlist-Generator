package com.example.playlistgeneratorv1.services;
import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.helpers.UserMapper;
import com.example.playlistgeneratorv1.models.RegisterDto;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private static final String DELETE_USER_ERROR_MESSAGE = "Only admin or User  can delete a user.";
    private static final String UPDATE_USER_ERROR_MESSAGE = "Only admin or User  can update a user.";
    private final UserRepository repository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper=userMapper;
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
    public User create(RegisterDto registerDto) {
        User newUser = UserMapper.fromDto(registerDto);
        repository.create(newUser);
        return newUser;
    }

    @Override
    public void update(int id, User user) {
        checkUpdatePermissions(id, user);
        repository.update(id, user);
    }
    @Override
    public void delete(int id, User user) {
        checkDeletePermissions(id, user);
        repository.delete(id);
    }
    private void checkUpdatePermissions(int userId, User updatingUser) {
        User existingUser = repository.get(userId);
        if (!(updatingUser.isAdmin() || existingUser.getUsername().equals(updatingUser.getUsername()))) {
            throw new AuthorizationException(UPDATE_USER_ERROR_MESSAGE);
        }
    }
    private void checkDeletePermissions(int userId, User deletingUser) {
        User existingUser = repository.get(userId);
        if (!(deletingUser.isAdmin() || existingUser.getUsername().equals(deletingUser.getUsername()))) {
            throw new AuthorizationException(DELETE_USER_ERROR_MESSAGE);
        }
    }



}
