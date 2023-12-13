package com.example.playlistgeneratorv1.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginDto {

    @NotEmpty(message = "Username name can't be empty!")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters long!")
    private String username;


    private String password;

    public LoginDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

