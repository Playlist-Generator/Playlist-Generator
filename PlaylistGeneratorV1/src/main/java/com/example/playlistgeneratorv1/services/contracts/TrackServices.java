package com.example.playlistgeneratorv1.services.contracts;

import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.models.User;


import java.util.List;
import java.util.Set;

public interface TrackServices {

    List<Tracks> get();

    Tracks get(int id);

    Tracks get(String title);

    List<Tracks> getByGenre(String genre);
    void create(Tracks tracks);

    void update (Tracks tracks);
    void delete (int id, User users);
    List<Tracks> findTopTrackAcrossGenres(int limit);


}
