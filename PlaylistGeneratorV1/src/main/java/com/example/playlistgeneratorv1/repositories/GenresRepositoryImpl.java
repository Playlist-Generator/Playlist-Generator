package com.example.playlistgeneratorv1.repositories;

import com.example.playlistgeneratorv1.exceptions.EntityNotFoundException;
import com.example.playlistgeneratorv1.models.Genres;
import com.example.playlistgeneratorv1.repositories.contracts.GenresRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GenresRepositoryImpl implements GenresRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public GenresRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Genres> getAllGenres() {
        try (Session session = sessionFactory.openSession()) {
            Query<Genres> query = session.createQuery("from Genres", Genres.class);
            return query.list();
        }
    }

    @Override
    public Genres get(int id) {
        try (Session session = sessionFactory.openSession()) {
            Genres genres = session.get(Genres.class, id);
            if (genres == null) {
                throw new EntityNotFoundException("Genres", id);
            }
            return genres;
        }
    }

    @Override
    @Transactional
    public void save(Genres genre) {
        try (Session session = sessionFactory.openSession()) {
            session.persist(genre);
        }
    }

    @Override
    public void create(Genres genre) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(genre);
            session.getTransaction().commit();
        }
    }
}
