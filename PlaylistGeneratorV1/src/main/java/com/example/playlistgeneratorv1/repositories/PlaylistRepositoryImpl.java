package com.example.playlistgeneratorv1.repositories;

import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.User;
import com.example.playlistgeneratorv1.repositories.contracts.PlaylistRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PlaylistRepositoryImpl implements PlaylistRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PlaylistRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Playlists> getByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createQuery(
                    "from Playlists where user.id = :userId", Playlists.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    @Override
    public Playlists get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Playlists playlist = session.get(Playlists.class, id);
            if (playlist == null) {
                throw new EntityNotFoundException("Playlists", id);
            }
            return playlist;
        }
    }

    @Override
    public Playlists create(Playlists playlist) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(playlist);
            session.getTransaction().commit();
        }
        return playlist;
    }

    @Override
    public void update(Playlists playlist) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(playlist);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Playlists id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Playlists playlist = session.get(Playlists.class, id);
            if (playlist == null) {
                throw new EntityNotFoundException("Playlists", "id","%d");
            }
            session.remove(playlist);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Playlists> findAll(Specification<Playlists> filters, Pageable pageable) {
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createQuery("from Playlists", Playlists.class);
            return query.list();
        }
    }

    @Override
    public List<Playlists> getUserPlaylists(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createNativeQuery("SELECT * FROM playlists p WHERE p.user_id = :id"
                    , Playlists.class);
            query.setParameter("id", id);
            return query.list();
        }
    }

    @Override
    public List<Playlists> getTopPlaylists() {
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createNativeQuery
                    ("SELECT * FROM playlists ORDER BY average_rank DESC LIMIT 10", Playlists.class);
            return query.list();
        }
    }

    @Override
    public int getPlaylistsCount() {
        try (Session session = sessionFactory.openSession()) {
            Query<Object> query = session.createQuery("SELECT COUNT(*) FROM Playlists");
            Object result = query.getSingleResult();
            return Integer.parseInt(result.toString());
        }
    }

    @Override
    public int getPlaylistsCountByGenre(long id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Object> query = session.createNativeQuery
                    ("SELECT COUNT(*) FROM playlists_genres p WHERE p.genre_id = :genre");
            query.setParameter("genre", id);
            Object result = query.getSingleResult();
            return Integer.parseInt(result.toString());
        }
    }

    @Override
    @Transactional
    public void transferPlaylistsToDeletedUser(User deletedUser, User userToDelete) {
        try (Session session = sessionFactory.openSession()) {
            Query<Playlists> query = session.createQuery
                    ("UPDATE Playlists l SET l.user = :deletedUser WHERE l.user = :userToDelete");
            query.setParameter("deletedUser", deletedUser);
            query.setParameter("userToDelete", userToDelete);
            query.executeUpdate();
        }
    }

//    @Override
//    public void addTrack(int playlistId, int trackId) {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Playlists playlist = session.get(Playlists.class, playlistId);
//            if (playlist == null) {
//                throw new EntityNotFoundException("Playlists", playlistId);
//            }
//
//            Tracks track = session.get(Tracks.class, trackId);
//            if (track == null) {
//                throw new EntityNotFoundException("Tracks", trackId);
//            }
//
//            playlist.addTrack(track);
//            session.getTransaction().commit();
//        }
//    }
//
//    @Override
//    public void removeTrack(int playlistId, int trackId) {
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//            Playlists playlist = session.get(Playlists.class, playlistId);
//            if (playlist == null) {
//                throw new EntityNotFoundException("Playlists", playlistId);
//            }
//
//            Tracks track = session.get(Tracks.class, trackId);
//            if (track == null) {
//                throw new EntityNotFoundException("Tracks", trackId);
//            }
//
//            playlist.removeTrack(track);
//            session.getTransaction().commit();
//        }
//    }
}
