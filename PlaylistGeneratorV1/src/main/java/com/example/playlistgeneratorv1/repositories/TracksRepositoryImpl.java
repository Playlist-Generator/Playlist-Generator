package com.example.playlistgeneratorv1.repositories;

import com.example.playlistgeneratorv1.models.Albums;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.repositories.contracts.TracksRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;

import java.util.List;

@Repository
public class TracksRepositoryImpl implements TracksRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TracksRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tracks> get() {
        try (Session session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery("from Tracks", Tracks.class);
            return query.list();
        }
    }

    @Override
    public Tracks get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Tracks track = session.get(Tracks.class, id);
            if (track == null) {
                throw new EntityNotFoundException("Tracks", id);
            }
            return track;
        }
    }

    @Override
    public Tracks get(String title) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery(" from Tracks where title = :title", Tracks.class);
            query.setParameter("title", title);

            List<Tracks> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Tracks", "title", title);
            }
            return result.get(0);
        }
    }

    @Override
    public List<Tracks> getByGenre(String genre) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery(
                    "SELECT t FROM Tracks t JOIN t.genre g WHERE g.genre = :genre", Tracks.class);
            query.setParameter("genre", genre);

            List<Tracks> tracks = query.list();
            if (tracks.isEmpty()) {
                throw new EntityNotFoundException("Tracks", "genre", genre);
            }
            return tracks;
        }
    }



    @Override
    public void create(Tracks track) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(track);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Tracks track) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(track);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Tracks track = session.get(Tracks.class, id);
            if (track == null) {
                throw new EntityNotFoundException("Track", id);
            }
            session.remove(track);
            session.getTransaction().commit();
        }
    }
}