package com.example.playlistgeneratorv1.models;

import java.sql.Time;
import java.util.Optional;

public class PlaylistFilterOptions {
    private Optional<String> name;
    private Optional<Time> duration;
    private Optional<String> genre;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public PlaylistFilterOptions(String name,
                                 Time duration,
                                 String genre,
                                 String sortBy,
                                 String sortOrder) {
        this.name = Optional.ofNullable(name);
        this.duration = Optional.ofNullable(duration);
        this.genre = Optional.ofNullable(genre);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);

    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<Time> getDuration() {
        return duration;
    }

    public Optional<String> getGenre() {
        return genre;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
}
