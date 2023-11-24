package com.example.playlistgeneratorv1.helpers;


import com.example.playlistgeneratorv1.models.RegisterDto;
import com.example.playlistgeneratorv1.models.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public Users fromDto(RegisterDto registerDto) {
        Users users = new Users();
        users.setUsername(registerDto.getUsername());
        users.setPassword(registerDto.getPassword());
        users.setEmail(registerDto.getEmail());
        return users;
    }

}
