package com.example.playlistgeneratorv1.repositories;

import com.example.playlistgeneratorv1.exceptions.EntityLongNotFoundException;
import com.example.playlistgeneratorv1.models.Tracks;
import com.example.playlistgeneratorv1.repositories.contracts.TracksRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;

import javax.sound.midi.Track;
import java.util.*;

@Repository
public class TracksRepositoryImpl implements TracksRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TracksRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tracks> getAll() {
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
    public Tracks get(long id) {
        try (Session session = sessionFactory.openSession()) {
            Tracks tracks = session.get(Tracks.class, id);
            if (tracks == null) {
                throw new EntityLongNotFoundException("Tracks", id);
            }
            return tracks;
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

    @Override
    public double findAveragePlayTimeForGenre(String genre) {
        try (Session session = sessionFactory.openSession()) {
            Query<Double> query = session.createQuery("SELECT AVG(t.duration) FROM Tracks t " +
                    "JOIN t.genre g WHERE g.genre = :genre", Double.class);
            query.setParameter("genre", genre);
            return query.getSingleResult();
        }
    }

    @Override
    public Set<Tracks> findTrackByGenreAndDistinctArtist(String genre, int limit) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery("SELECT t FROM Tracks t " +
                    "JOIN t.genre g WHERE g.genre = :genre " +
                    "GROUP BY t.artist.id ORDER BY RANDOM()", Tracks.class);
            query.setParameter("genre", genre);
            query.setMaxResults(limit);
            return Set.copyOf(query.getResultList());
        }
    }

    @Override
    public Set<Tracks> findTopTrackByGenreAndDistinctArtist(String genre, int limit) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery("SELECT t FROM Tracks t " +
                    "JOIN t.genre g WHERE g.genre = :genre " +
                    "GROUP BY t.artist.id ORDER BY t.ranks DESC", Tracks.class);
            query.setParameter("genre", genre);
            query.setMaxResults(limit);
            return Set.copyOf(query.getResultList());
        }
    }

    @Override
    public Set<Tracks> findTrackByGenre(String genre, int limit) {
        try (Session session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery("SELECT t FROM Tracks t " +
                    "JOIN t.genre g WHERE g.genre = :genre " +
                    "ORDER BY RANDOM()", Tracks.class);
            query.setParameter("genre", genre);
            query.setMaxResults(limit);
            return Set.copyOf(query.getResultList());
        }
    }
    @Override
    public List<String> getAllGenresFromTracks(List<Tracks> tracks) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT g.genre FROM Tracks t JOIN t.genre g WHERE t IN (:tracks)";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameterList("tracks", tracks);
            return query.list();
        }
    }
    @Override
    public Map<String, List<Tracks>> getTracksByGenres(List<String> genres) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT t FROM Tracks t JOIN t.genre g WHERE g.genre IN (:genres)";
            Query<Tracks> query = session.createQuery(hql, Tracks.class);
            query.setParameterList("genres", genres);
            List<Tracks> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("Tracks", "genres", genres.toString());
            }
            Map<String, List<Tracks>> tracksByGenre = new HashMap<>();
            for (String genre : genres) {
                List<Tracks> genreTracks = new ArrayList<>();
                for (Tracks track : result) {
                    if (track.getGenre().getGenre().equals(genre)) {
                        genreTracks.add(track);
                    }
                }
                tracksByGenre.put(genre, genreTracks);
            }

            return tracksByGenre;
        }
    }


    @Override
    public Set<Tracks> findTopTrackByGenre(String genre, int limit) {
        try (var session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery(
                    "SELECT t FROM Tracks t " +
                            "JOIN t.genre g WHERE g.genre = :genre " +
                            "ORDER BY t.ranks DESC", Tracks.class);
            query.setParameter("genre", genre);
            query.setMaxResults(limit);
            return Set.copyOf(query.getResultList());
        }
    }

    @Override
    public List<Tracks> findTopTrackAcrossGenres(int limit) {
        try (var session = sessionFactory.openSession()) {
            Query<Tracks> query = session.createQuery("SELECT t FROM Tracks t " +
                    "ORDER BY t.ranks DESC", Tracks.class);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

}