package com.example.playlistgeneratorv1.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;


public class PlaylistsDto {
    @NotEmpty(message = "Name can't be empty")
    private String name;

    public PlaylistsDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
