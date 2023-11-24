INSERT INTO Users (id, username, password, email, isadmin)
VALUES (1, 'johnDoe', 'password123', 'johndoe@example.com', false),
       (2, 'janeSmith', 'qwerty456', 'janesmith@example.com', true);

INSERT INTO Playlists (id, user_id, title, tags, total_playtime, average_rank)
    VALUES (1, 1, 'Workout Playlist', 'workout, fitness', 120, 4), (2, 2, 'Relaxing Playlist', 'relax, calm', 60, 5);


INSERT INTO Artist (id, name, url) VALUES (1, 'Artist 1', 'https://artist1.com'), (2, 'Artist 2', 'https://artist2.com'), (3, 'Artist 3', 'https://artist3.com');

INSERT INTO Album (id, title, track_list) VALUES (1, 'Album 1', 'Song 1, Song 2'), (2, 'Album 2', 'Song 3');

INSERT INTO Genres (genre_id, genre) VALUES (1, 'Pop'), (2, 'Rock');

INSERT INTO Tracks (id, playlist_id, album_id, genre_id, artist_id, title, link, artist, duration, ranks, preview_url) VALUES (1, 1, 1, 1, 1, 'Song 1', 'https://song1.com', 'Artist 1', 180, 4, 'https://song1preview.com'), (2, 1, 1, 1, 2, 'Song 2', 'https://song2.com', 'Artist 2', 210, 5, 'https://song2preview.com'), (3, 2, 2, 2, 3, 'Song 3', 'https://song3.com', 'Artist 3', 150, 3, 'https://song3preview.com');