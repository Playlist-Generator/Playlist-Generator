CREATE TABLE Users (
                       id INT PRIMARY KEY,
                       username VARCHAR(255),
                       password VARCHAR(255),
                       email VARCHAR(255),
                       isadmin BOOLEAN
);

CREATE TABLE Playlists (
                           id INT PRIMARY KEY,
                           user_id INT,
                           title VARCHAR(255),
                           tags VARCHAR(255),
                           total_playtime INT,
                           average_rank INT,
                           FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE Tracks (
                        id INT PRIMARY KEY,
                        playlist_id INT,
                        album_id INT,
                        genre_id INT,
                        artist_id INT,
                        title VARCHAR(255),
                        link VARCHAR(255),
                        artist VARCHAR(255),
                        duration BIGINT,
                        ranks INT,
                        preview_url VARCHAR(255),
                        FOREIGN KEY (playlist_id) REFERENCES Playlists(id),
                        FOREIGN KEY (genre_id) REFERENCES Genres(genre_id),
                        FOREIGN KEY (album_id) REFERENCES Album(id),
                        FOREIGN KEY (artist_id) REFERENCES Artist(id)
);

CREATE TABLE Artist (
                        id INT PRIMARY KEY,
                        name VARCHAR(255),
                        url VARCHAR(255)
);

CREATE TABLE Album (
                       id INT PRIMARY KEY,
                       title VARCHAR(255),
                       track_list VARCHAR(255)
);

CREATE TABLE Genres (
                        genre_id INT PRIMARY KEY,
                        genre VARCHAR(255)
);