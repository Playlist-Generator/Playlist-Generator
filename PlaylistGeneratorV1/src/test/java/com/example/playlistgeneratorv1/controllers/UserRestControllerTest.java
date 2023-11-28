package com.example.playlistgeneratorv1.controllers;

import com.example.playlistgeneratorv1.controller.rest.UserRestController;
import com.example.playlistgeneratorv1.exceptions.AuthorizationException;
import com.example.playlistgeneratorv1.helpers.AuthenticationHelper;
import com.example.playlistgeneratorv1.models.RegisterDto;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.services.contracts.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserRestControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationHelper authenticationHelper;

    @InjectMocks
    private UserRestController userRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_Admin() {
        HttpHeaders headers = new HttpHeaders();
        User adminUser = new User();
        adminUser.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(adminUser);

        List<User> userList = Arrays.asList(new User(), new User());
        when(userService.get()).thenReturn(userList);

        List<User> result = userRestController.get(headers);


        assertEquals(userList, result);
    }

    @Test
    void getUsers_NonAdmin() {
        HttpHeaders headers = new HttpHeaders();
        User nonAdminUser = new User();
        nonAdminUser.setAdmin(false);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(nonAdminUser);

        assertThrows(ResponseStatusException.class, () -> userRestController.get(headers));
    }

    @Test
    void getUserById_Admin() {
        HttpHeaders headers = new HttpHeaders();
        User adminUser = new User();
        adminUser.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(adminUser);

        int userId = 1;
        User user = new User();
        when(userService.get(userId)).thenReturn(user);

        User result = userRestController.get(headers, userId);

        assertEquals(user, result);
    }

    @Test
    void getUserById_SelfUser() {
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setId(1);
        user.setAdmin(false);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);

        int userId = 1;
        when(userService.get(userId)).thenReturn(user);

        User result = userRestController.get(headers, userId);

        assertEquals(user, result);
    }

    @Test
    void getUserById_NonAdmin_NonSelfUser() {
        HttpHeaders headers = new HttpHeaders();
        User nonAdminUser = new User();
        nonAdminUser.setAdmin(false);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(nonAdminUser);

        int userId = 2;
        User user = new User();
        user.setId(1);
        user.setAdmin(false);
        when(userService.get(userId)).thenReturn(user);

        assertThrows(ResponseStatusException.class, () -> userRestController.get(headers, userId));
    }

    @Test
    void createUser() {
        RegisterDto registerDto = new RegisterDto();
        User newUser = new User();
        when(userService.create(registerDto)).thenReturn(newUser);

        ResponseEntity<User> result = userRestController.create(registerDto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(newUser, result.getBody());
    }

    @Test
    void createUser_Unauthorized() {
        RegisterDto registerDto = new RegisterDto();
        doThrow(new AuthorizationException(UserRestController.ERROR_MESSAGE))
                .when(userService).create(registerDto);

        assertThrows(ResponseStatusException.class, () -> userRestController.create(registerDto));
    }




    @Test
    void updateUser_Unauthorized() {
        HttpHeaders headers = new HttpHeaders();
        User nonAdminUser = new User();
        nonAdminUser.setAdmin(false);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(nonAdminUser);

        int userId = 2;
        User updatedUser = new User();
        doThrow(new AuthorizationException(UserRestController.ERROR_MESSAGE))
                .when(userService).update(userId, updatedUser);

        assertThrows(ResponseStatusException.class, () -> userRestController.update(userId, updatedUser, headers));
    }

    @Test
    void deleteUser_Admin() {

        HttpHeaders headers = new HttpHeaders();
        User adminUser = new User();
        adminUser.setAdmin(true);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(adminUser);

        int userId = 1;
        ResponseEntity<User> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        doNothing().when(userService).delete(userId, adminUser);

        userRestController.delete(headers, userId);

    }

    @Test
    void deleteUser_NonAdmin_SelfUser() {
        HttpHeaders headers = new HttpHeaders();
        User user = new User();
        user.setId(1);
        user.setAdmin(false);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(user);

        int userId = 1;
        ResponseEntity<User> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        doNothing().when(userService).delete(userId, user);

        userRestController.delete(headers, userId);

    }

    @Test
    void deleteUser_Unauthorized() {
        HttpHeaders headers = new HttpHeaders();
        User nonAdminUser = new User();
        nonAdminUser.setAdmin(false);
        when(authenticationHelper.tryGetUser(headers)).thenReturn(nonAdminUser);

        int userId = 2;
        doThrow(new AuthorizationException(UserRestController.ERROR_MESSAGE))
                .when(userService).delete(userId, nonAdminUser);

        assertThrows(ResponseStatusException.class, () -> userRestController.delete(headers, userId));
    }
}
