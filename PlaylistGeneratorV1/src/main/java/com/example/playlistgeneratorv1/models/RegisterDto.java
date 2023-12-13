package com.example.playlistgeneratorv1.models;

import jakarta.validation.constraints.NotEmpty;

public class RegisterDto extends LoginDto  {

    @NotEmpty(message = "Password confirmation can't be empty")

    private String passwordConfirm;

    @NotEmpty(message = "Email can't be empty")
    private String email;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
