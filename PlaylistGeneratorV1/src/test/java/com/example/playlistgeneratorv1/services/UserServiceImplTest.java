package com.example.playlistgeneratorv1.services;

import com.example.playlistgeneratorv1.helpers.UserMapper;
import com.example.playlistgeneratorv1.models.RegisterDto;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers() {
        List<User> userList = Arrays.asList(new User(), new User());
        when(userRepository.get()).thenReturn(userList);

        List<User> result = userService.get();

        assertEquals(userList, result);
    }

    @Test
    void getUserById() {
        int userId = 1;
        User user = new User();
        when(userRepository.get(userId)).thenReturn(user);

        User result = userService.get(userId);

        assertEquals(user, result);
    }

    @Test
    void getUserByName() {
        String username = "testUser";
        User user = new User();
        when(userRepository.get(username)).thenReturn(user);

        User result = userService.get(username);

        assertEquals(user, result);
    }

    @Test
    void createUser() {
        RegisterDto registerDto = new RegisterDto();
        User newUser = new User();
        when(userMapper.fromDto(registerDto)).thenReturn(newUser);

        User result = userService.create(registerDto);

        assertEquals(newUser, result);
        verify(userRepository, times(1)).create(newUser);
    }




}
