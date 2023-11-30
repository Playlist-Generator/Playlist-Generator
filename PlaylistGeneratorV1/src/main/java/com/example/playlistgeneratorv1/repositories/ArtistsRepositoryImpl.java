package com.example.playlistgeneratorv1.repositories;

import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Artists;
import com.example.playlistgeneratorv1.repositories.contracts.ArtistsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class ArtistsRepositoryImpl implements ArtistsRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ArtistsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Artists> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Artists", Artists.class);
            return query.getResultList();
        }
    }

    @Override
    public Artists getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Artists artist = session.get(Artists.class, id);
            if (artist == null) {
                throw new EntityNotFoundException("Artists", id);
            }
            return artist;
        }
    }

    @Override
    public Artists getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Artists where name = :name", Artists.class);
            query.setParameter("name", name);

            List<Artists> artists = query.getResultList();
            if (artists.isEmpty()) {
                throw new EntityNotFoundException("Artists", "name", name);
            }
            return artists.get(0);
        }
    }

    @Override
    public void create(Artists artist) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(artist);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Artists artist) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(artist);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Artists artist = session.get(Artists.class, id);
            if (artist == null) {
                throw new EntityNotFoundException("Artist", id);
            }
            session.remove(artist);
            session.getTransaction().commit();
        }
    }
}
