package com.example.playlistgeneratorv1.repositories;

import com.example.playlistgeneratorv1.models.Playlists;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.repositories.contracts.PlaylistRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;

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
    public void create(Playlists playlist) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(playlist);
            session.getTransaction().commit();
        }
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
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Playlists playlist = session.get(Playlists.class, id);
            if (playlist == null) {
                throw new EntityNotFoundException("Playlists", id);
            }
            session.remove(playlist);
            session.getTransaction().commit();
        }
    }

    @Override
    public void addTrack(int playlistId, int trackId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Playlists playlist = session.get(Playlists.class, playlistId);
            if (playlist == null) {
                throw new EntityNotFoundException("Playlists", playlistId);
            }

            Tracks track = session.get(Tracks.class, trackId);
            if (track == null) {
                throw new EntityNotFoundException("Tracks", trackId);
            }

            playlist.addTrack(track);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeTrack(int playlistId, int trackId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Playlists playlist = session.get(Playlists.class, playlistId);
            if (playlist == null) {
                throw new EntityNotFoundException("Playlists", playlistId);
            }

            Tracks track = session.get(Tracks.class, trackId);
            if (track == null) {
                throw new EntityNotFoundException("Tracks", trackId);
            }

            playlist.removeTrack(track);
            session.getTransaction().commit();
        }
    }
}
