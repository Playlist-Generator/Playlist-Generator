package com.example.playlistgeneratorv1.repositories.contracts;
import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

    public interface PlaylistRepository {
        List<Playlists> getByUser(int userId);
        Playlists get(int id);
        Playlists create(Playlists playlist);
        void update(Playlists playlist);
        void delete(Playlists id);

        List<Playlists> findAll(Specification<Playlists> filters, Pageable pageable);

        List<Playlists> getUserPlaylists(int id);

        List<Playlists> getTopPlaylists();

        int getPlaylistsCount();

        int getPlaylistsCountByGenre(long id);

        @Transactional
        void transferPlaylistsToDeletedUser(User deletedUser, User userToDelete);
//        void addTrack(int playlistId, int trackId);
//        void removeTrack(int playlistId, int trackId);
    }

